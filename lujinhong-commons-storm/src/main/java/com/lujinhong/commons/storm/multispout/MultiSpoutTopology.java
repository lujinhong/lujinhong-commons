package com.lujinhong.commons.storm.multispout;

import java.util.Arrays;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.tuple.Fields;
import storm.kafka.BrokerHosts;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import storm.kafka.trident.TransactionalTridentKafkaSpout;
import storm.kafka.trident.TridentKafkaConfig;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.spout.ITridentSpout;

public class MultiSpoutTopology {

	private static ResourceBundle bundle = ResourceBundle.getBundle("conf");
	private static final Logger LOG = LoggerFactory.getLogger(MultiSpoutTopology.class);

	private StormTopology buildTopology() {
		// 1、定义kafkaspout的相关配置
		BrokerHosts zkHost = new ZkHosts(bundle.getString("kafka_zk"));
		String topic = bundle.getString("topic");
		TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(zkHost, topic, "storm");
		kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		TransactionalTridentKafkaSpout kafkaSpout = new TransactionalTridentKafkaSpout(kafkaConfig);
		LOG.info("Build spout with kafka_zk:{}, topic:{}", bundle.getString("kafka_zk"), topic);

		// 2、设置第2个kafkaspout
		TridentKafkaConfig kafkaConfig2 = new TridentKafkaConfig(zkHost, "streaming_g17_sdc", "storm2");
		kafkaConfig2.scheme = new SchemeAsMultiScheme(new StringScheme());
		TransactionalTridentKafkaSpout kafkaSpout2 = new TransactionalTridentKafkaSpout(kafkaConfig2);

		// 3、merge2个kafkaspout
		TridentTopology topology = new TridentTopology();
		Stream firstStream = topology.newStream("kafka", kafkaSpout);
		Stream secondStream = topology.newStream("kafka2", kafkaSpout2);
		topology.merge(firstStream, secondStream).each(new Fields("str"), new PrepaidFunction(), new Fields("word")).parallelismHint(6);

		// 4、设置第3个spout（自定义spout)
		ITridentSpout mySpout = new TimerSpout();
		topology.newStream("myspout", mySpout).broadcast().each(new Fields("timer"), new PrepaidFunction(),
				new Fields("word")).parallelismHint(6);

		return topology.build();
	}

	public static void main(String[] args) throws Exception {

		MultiSpoutTopology topology = new MultiSpoutTopology();
		Config config = new Config();
		config.put(Config.TOPOLOGY_TRIDENT_BATCH_EMIT_INTERVAL_MILLIS, 2000);

		if (args != null && args.length > 0 && args[0].equals("cluster")) {
			String topologyName = bundle.getString("topo_name");
			String nimbus = bundle.getString("nimbus");
			String stormZk = bundle.getString("storm_zk");

			config.setNumWorkers(3);
			config.setMaxTaskParallelism(5);
			config.put(Config.NIMBUS_HOST, nimbus);
			config.put(Config.NIMBUS_THRIFT_PORT, 6627);
			config.put(Config.STORM_ZOOKEEPER_PORT, 2181);
			config.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList(stormZk));
			StormSubmitter.submitTopology(topologyName, config, topology.buildTopology());
			LOG.info("submited topology with config:\nnimbus:{},storm_zk:{}", nimbus, stormZk);
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("kafka", config, topology.buildTopology());
		}
	}

}
