package com.lujinhong.commons.storm.kinit;

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
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.Split;

import java.util.Arrays;



public class StormKinit {

	private final BrokerHosts brokerHosts;
	private final static String KAFKA_ZK = "1.1.1.1:2181/kafka2";
	private final static String TOPO_NAME = "ljhtest";
	private final static String NIMBUS_HOST = "1.1.1.1";
	

	public StormKinit(String kafkaZookeeper) {
		brokerHosts = new ZkHosts(kafkaZookeeper);
	}

	public StormTopology buildTopology() {
		// 1、定义kafka的相关配置
		TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(brokerHosts, "streaming_g18_sdc", "storm");
		kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		kafkaConfig.ignoreZkOffsets = true;
		//默认值为2M，此时数据太大，读写hbase会超时。
		kafkaConfig.fetchSizeBytes = 10240;
		TransactionalTridentKafkaSpout kafkaSpout = new TransactionalTridentKafkaSpout(kafkaConfig);
		
		// 2、定义拓扑，进行单词统计后，写入一个分布式内存中。
		TridentTopology topology = new TridentTopology();
		topology.newStream("ljhtest2", kafkaSpout).shuffle().
			each(new Fields("str"), new PrepaidFunction(), new Fields("server","cash"));

		return topology.build();
	}



	public static void main(String[] args) throws Exception {

		StormKinit sentenceAggregationTopology = new StormKinit(KAFKA_ZK);
		Config config = new Config();
		config.put(Config.TOPOLOGY_TRIDENT_BATCH_EMIT_INTERVAL_MILLIS, 2000);
		config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 3);
		System.out.println(System.getProperty("java.class.path"));

		if (args != null && args.length > 1) {
			config.setNumWorkers(3);
			config.setMaxTaskParallelism(5);
			config.put(Config.NIMBUS_HOST, NIMBUS_HOST);
			config.put(Config.NIMBUS_THRIFT_PORT, 6627);
			config.put(Config.STORM_ZOOKEEPER_PORT, 2181);
			config.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList("1.1.1.1"));
			StormSubmitter.submitTopology(TOPO_NAME, config, sentenceAggregationTopology.buildTopology());
		} else {
			config.setNumWorkers(2);
			config.setMaxTaskParallelism(2);
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("kafka", config, sentenceAggregationTopology.buildTopology());

		}
	}
}
