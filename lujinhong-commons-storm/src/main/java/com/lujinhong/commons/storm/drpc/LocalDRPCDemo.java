package com.lujinhong.commons.storm.drpc;

import java.util.HashMap;

import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.drpc.LinearDRPCTopologyBuilder;
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

public class LocalDRPCDemo {

	public static void main(String[] args) {
		//创建DRPC服务器
		LocalDRPC drpc = new LocalDRPC();

		//1、创建拓扑
		TridentTopology topology = new TridentTopology();
		topology.newDRPCStream("exclaimation", drpc).each(new Fields("args"), new ExclaimBolt(), new Fields("words"))
				.parallelismHint(5);

		//2、提交拓扑
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("drpc-demo", new HashMap(), topology.build());
		
		//3、客户端调用
		String ss = "the cow jumped over the moon";
		for(String s :ss.split(" ")){
			//返回结果是一个KV格式，KEY为请求的id, V为最后一个bolt的返回结果。被封装成一个2个值的tuple。
			System.out.println(drpc.execute("exclaimation", s));
		}
		
		//4、关闭集群及drpc服务器
		cluster.shutdown();
		drpc.shutdown();

	}

}

 class ExclaimBolt extends BaseFunction{

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String input = tuple.getString(0);
		collector.emit(new Values(input+"!"));
	}

}
