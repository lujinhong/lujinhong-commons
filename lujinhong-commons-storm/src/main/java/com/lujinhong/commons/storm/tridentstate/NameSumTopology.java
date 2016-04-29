package com.lujinhong.commons.storm.tridentstate;

import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/*
 * 本拓扑统计了每个名字出现的次数。
 * 主要用于演示了Trident State的用法，以及partitionAggregate的用法。
 */
public class NameSumTopology {

	private StormTopology buildTopology() {
		FixedBatchSpout spout = new FixedBatchSpout(
				new Fields("msg"),
				4,
				new Values(
						"{\"name\":\"jason\",\"age\":20,\"title\":\"op\",\"tel\":\"15810005758\"}"),
				new Values(
						"{\"name\":\"john\",\"age\":20,\"title\":\"rd\",\"tel\":\"13254568412\"}"),
				new Values(
						"{\"name\":\"joe\",\"age\":21,\"title\":\"rd\",\"tel\":\"18756427\"}"),
				new Values(
						"{\"name\":\"nancy\",\"age\":22,\"title\":\"hr\",\"tel\":\"13698756254\"}"),
				new Values(
						"{\"name\":\"lucy\",\"age\":23,\"title\":\"op\",\"tel\":\"132547524787\"}"),
				new Values(
						"{\"name\":\"ken\",\"age\":24,\"title\":\"op\",\"tel\":\"1552368742554\"}"),
				new Values(
						"{\"name\":\"hawk\",\"age\":20,\"title\":\"ceo\",\"tel\":\"159456852356\"}"),
				new Values(
						"{\"name\":\"rocky\",\"age\":22,\"title\":\"cto\",\"tel\":\"1325687456\"}"),
				new Values(
						"{\"name\":\"chris\",\"age\":23,\"title\":\"cfo\",\"tel\":\"13756425896\"}"),
				new Values(
						"{\"name\":\"jason\",\"age\":20,\"title\":\"coo\",\"tel\":\"13565478952\"}"),
				new Values(
						"{\"name\":\"leo\",\"age\":21,\"title\":\"cxo\",\"tel\":\"13452687922\"}"));

		spout.setCycle(true);

		TridentTopology topology = new TridentTopology();

		storm.trident.Stream Origin_Stream = topology
				.newStream("tridentStateDemoId", spout)
				.parallelismHint(3)
				.shuffle()
				.parallelismHint(3)
				.each(new Fields("msg"), new Splitfield(),
						new Fields("name", "age", "title", "tel"))
				.parallelismHint(3)
				.project(new Fields("name")) //其实没什么必要，上面就不需要发射BCD字段，但可以示范一下project的用法
				.parallelismHint(3)
				.partitionBy(new Fields("name"));   //根据name的值作分区

		Origin_Stream.partitionAggregate(new Fields("name"), new NameCountAggregator(),
				new Fields("nameSumKey", "nameSumValue")).partitionPersist(
				new NameSumStateFactory(), new Fields("nameSumKey", "nameSumValue"),
				new NameSumUpdater());

		return topology.build();

	}

	public static void main(String[] args) {

		NameSumTopology MyTopology = new NameSumTopology();
		Config config = new Config();

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("kafka-storm", config,
				MyTopology.buildTopology());

	}

}
