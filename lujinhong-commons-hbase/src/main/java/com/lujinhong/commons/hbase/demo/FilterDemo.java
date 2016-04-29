package com.lujinhong.commons.hbase.demo;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.util.Bytes;

public class FilterDemo {

	private static final String TABLE_NAME = "test1";
	private static final String FAMILY = "sf";
	private static Connection connection = null;

	public static Connection getConnection(Configuration config) throws IOException {
		if (connection == null) {
			connection = ConnectionFactory.createConnection(config);
		}
		return connection;
	}



	public static void scanTableByFilter(Connection connection, Filter filter) throws IOException {
		try (Table table = connection.getTable(TableName.valueOf(TABLE_NAME));) {
			Scan s = new Scan();
			s.setFilter(filter);
			// 如果不指定column则返回全部数据
			// s.addColumn(Bytes.toBytes(FAMILY), Bytes.toBytes("c1"));
			
			try (ResultScanner scanner = table.getScanner(s);) {
				for (Result rr : scanner) {
					System.out.println("Found row: " + Bytes.toString(rr.getValue(Bytes.toBytes(FAMILY), Bytes.toBytes("c1"))));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Configuration config = HBaseConfiguration.create();
		config.addResource(new Path("/Users/liaoliuqing/Downloads/conf_loghbase/hbase", "hbase-site.xml"));
		config.addResource(new Path("/Users/liaoliuqing/Downloads/conf_loghbase", "core-site.xml"));

		try (Connection connection = getConnection(config)) {
			scanTableByFilter(connection,new PrefixFilter(Bytes.toBytes("user1")));
		}
	}

}
