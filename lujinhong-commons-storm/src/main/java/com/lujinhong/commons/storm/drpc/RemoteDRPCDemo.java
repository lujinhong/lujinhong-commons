package com.lujinhong.commons.storm.drpc;

import java.util.HashMap;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.drpc.LinearDRPCTopologyBuilder;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * date: 2016年7月4日 上午10:39:43
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified: 2016年7月4日
 *         上午10:39:43
 */

public class RemoteDRPCDemo {

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		
		Config conf = new Config();
		conf.setNumWorkers(5);

		//1、创建拓扑
		TridentTopology topology = new TridentTopology();
		topology.newDRPCStream("exclaimation").each(new Fields("args"), new ExclaimBolt2(), new Fields("words"))
				.parallelismHint(5);

		//2、提交拓扑
		StormSubmitter.submitTopology("drpc-demo", conf, topology.build());
		

	}

}

 class ExclaimBolt2 extends BaseFunction{

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String input = tuple.getString(0);
		collector.emit(new Values(input+"!"));
	}

}
