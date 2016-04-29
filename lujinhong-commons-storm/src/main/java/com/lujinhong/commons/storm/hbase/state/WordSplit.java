package com.lujinhong.commons.storm.hbase.state;

import java.io.IOException;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Table;
//import org.apache.hadoop.hbase.util.Bytes;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;


import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class WordSplit extends BaseFunction {
	private static int count = 0;
	//private static Connection connection = null;
	//private static Table table = null;
	
	private final static String TABLE_NAME = "ljhtest";
	private final static String FAMILY = "f1";
	private final static String QUALIFIER = "count";

//	public void prepare(Map conf, TridentOperationContext context) {
//		super.prepare(conf, context);
//		if (table == null) {
//			Configuration config = HBaseConfiguration.create();
//			config.addResource(new Path("/Users/liaoliuqing/Downloads/conf_loghbase/hbase", "hbase-site.xml"));
//			config.addResource(new Path("/Users/liaoliuqing/Downloads/conf_loghbase", "core-site.xml"));
//			try {
//				connection = ConnectionFactory.createConnection(config);
//				table = connection.getTable(TableName.valueOf(TABLE_NAME));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String sentence = (String) tuple.getValue(0);
		if (sentence != null) {
			sentence = sentence.replaceAll("\r", "");
			sentence = sentence.replaceAll("\n", "");
			for (String word : sentence.split(" ")) {
				if(word.length() != 0)
				collector.emit(new Values(word));
			}
			count++;
			if (count % 100 == 0) {
				System.out.println(count + " message done!");
//				try {
//					HBaseUtils.putDataToHbase(table, "count", FAMILY, QUALIFIER, Bytes.toBytes(count + " message done!"));
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}
	}




}