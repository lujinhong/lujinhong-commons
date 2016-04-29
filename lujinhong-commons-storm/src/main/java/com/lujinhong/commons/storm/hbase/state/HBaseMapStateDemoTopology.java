package com.lujinhong.commons.storm.hbase.state;

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
import storm.trident.testing.Split;

import java.util.Arrays;

import org.apache.storm.hbase.trident.mapper.SimpleTridentHBaseMapMapper;
import org.apache.storm.hbase.trident.state.HBaseMapState;


public class HBaseMapStateDemoTopology {

	private final BrokerHosts brokerHosts;

	public HBaseMapStateDemoTopology(String kafkaZookeeper) {
		brokerHosts = new ZkHosts(kafkaZookeeper);
	}

	public StormTopology buildTopology() {
		// 1、定义kafka的相关配置
		TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(brokerHosts, "ma43", "storm");
		kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		//默认值为2M，此时数据太大，读写hbase会超时。
		kafkaConfig.fetchSizeBytes = 10240;
		TransactionalTridentKafkaSpout kafkaSpout = new TransactionalTridentKafkaSpout(kafkaConfig);
		
		HBaseMapState.Options option = new HBaseMapState.Options();
		option.tableName = "ljhtest2";
		option.columnFamily = "f1";
		option.mapMapper = new SimpleTridentHBaseMapMapper("ms");
		//System.setProperty(option.configKey, "/Users/liaoliuqing/Downloads/conf_loghbase/hbase/hbase-site.xml");
		// 2、定义拓扑，进行单词统计后，写入一个分布式内存中。
		TridentTopology topology = new TridentTopology();
        topology.newStream("kafka", kafkaSpout).shuffle().
                each(new Fields("str"), new WordSplit(), new Fields("word")).
                groupBy(new Fields("word")).
                persistentAggregate(HBaseMapState.transactional(option), new Count(), new Fields("aggregates_words")).parallelismHint(1);

		return topology.build();
	}

	private final static String KAFKA_ZK = "1.1.1.1:2181/kafka";
	private final static String TOPO_NAME = "ljhtest";
	private final static String NIMBUS_HOST = "1.1.1.1";

	public static void main(String[] args) throws Exception {

		HBaseMapStateDemoTopology sentenceAggregationTopology = new HBaseMapStateDemoTopology(KAFKA_ZK);
		Config config = new Config();
		config.put(Config.TOPOLOGY_TRIDENT_BATCH_EMIT_INTERVAL_MILLIS, 2000);
		config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 3);

		if (args != null && args.length > 1) {
			config.setNumWorkers(2);
			config.setMaxTaskParallelism(5);
			config.put(Config.NIMBUS_HOST, NIMBUS_HOST);
			config.put(Config.NIMBUS_THRIFT_PORT, 6627);
			config.put(Config.STORM_ZOOKEEPER_PORT, 2181);
			config.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList(NIMBUS_HOST));
			StormSubmitter.submitTopology(TOPO_NAME, config, sentenceAggregationTopology.buildTopology());
		} else {
			config.setNumWorkers(2);
			config.setMaxTaskParallelism(2);
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("kafka", config, sentenceAggregationTopology.buildTopology());

		}
	}
}
