package com.lujinhong.commons.storm.drpc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

/**
 * date: 2016年7月4日 上午10:39:43
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified: 2016年7月4日
 *         上午10:39:43
 */

public class DRPCHbaseGetter {

	public static void main(String[] args)
			throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {

		//本地运行
		// 创建DRPC服务器
//		LocalDRPC drpc = new LocalDRPC();
//
//		// 1、创建拓扑
//		TridentTopology topology = new TridentTopology();
//		topology.newDRPCStream("hbase-getter", drpc).each(new Fields("args"), new HBaseGetter(), new Fields("words"))
//				.parallelismHint(5);
//
//		// 2、提交拓扑
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("drpc-demo", new HashMap(), topology.build());
//
//		System.out.println(drpc.execute("hbase-getter", "r101548"));
//
//		cluster.shutdown();
//		drpc.shutdown();

		//集群方式运行
		 Config conf = new Config();
		 conf.setNumWorkers(5);

		// // 1、创建拓扑
		 TridentTopology topology = new TridentTopology();
		 topology.newDRPCStream("hbase-getter").each(new Fields("args"), new
		 HBaseGetter(), new Fields("words"))
		 .parallelismHint(5);
		
		// // 2、提交拓扑
		 StormSubmitter.submitTopology("drpc-hbase-getter", conf,
		 topology.build());

	}

}

class HBaseGetter extends BaseFunction {

	private static HBaseHelper helper = null;
	private static Connection connection = null;
	Table table = null;
	private static final String TABLE_NAME = "ljhtest2";

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String rowkey = tuple.getString(0);
		Get get = new Get(Bytes.toBytes(rowkey));
		get.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("c1"));

		try {
			collector.emit(new Values(table.get(get)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		super.prepare(conf, context);
		if (helper == null) {
			Configuration config = HBaseConfiguration.create();
			config.addResource(new Path("/Users/liaoliuqing/Downloads/conf_loghbase/hbase", "hbase-site.xml"));

			try {
				helper = HBaseHelper.getHelper(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (connection == null) {
			connection = helper.getConnection();
		}
		if (table == null) {
			try {
				table = connection.getTable(TableName.valueOf(TABLE_NAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void cleanup() {
		super.cleanup();
		try {
			connection.close();
			helper.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
