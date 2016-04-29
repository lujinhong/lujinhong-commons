package com.lujinhong.commons.storm.trident.resourcedistribute;


import java.util.Arrays;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;

/*
 * 示范了如何将一个第三大方jar包及一个文件打包至jar包，并在集群上读取文件的方法。
 */
public class ResourceDistribution {
	

	private final static String TOPO_NAME = "ljhtest";
	private final static String NIMBUS_HOST = "1.1.1.1";

	public StormTopology buildTopology() {
		
		FixedBatchSpout spout =
                new FixedBatchSpout(new Fields("ip"), 3, new Values(
                        "1.1.172.122"), new Values(
                        "1.1.172.121"), new Values(
                        "24.24.24.24"),
                        new Values("12.1.172.122"), new Values(
                                "21.21.21.21"));
		spout.setCycle(true);

		TridentTopology topology = new TridentTopology();
        topology.newStream("ipcheck", spout).shuffle().
                each(new Fields("ip"), new GetAndPrintIPInformation(), new Fields("information"));

		return topology.build();
	}


	public static void main(String[] args) throws Exception {

		ResourceDistribution topology = new ResourceDistribution();
		Config config = new Config();

		if (args != null && args.length > 1) {
			config.setNumWorkers(2);
			config.setMaxTaskParallelism(5);
			config.put(Config.NIMBUS_HOST, NIMBUS_HOST);
			config.put(Config.NIMBUS_THRIFT_PORT, 6627);
			config.put(Config.STORM_ZOOKEEPER_PORT, 2181);
			config.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList("1.1.1.1"));
			StormSubmitter.submitTopology(TOPO_NAME, config, topology.buildTopology());
		} else {
			config.setNumWorkers(2);
			config.setMaxTaskParallelism(2);
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("kafka", config, topology.buildTopology());

		}
	}
}
