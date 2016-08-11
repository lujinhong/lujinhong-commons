package com.lujinhong.commons.hbase.demo;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * date: 2016年7月12日 下午12:24:19
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年7月12日 下午12:24:19
 */

public class SdcTest {

	public static void main(String[] args) throws IOException {

		Configuration conf = HBaseConfiguration.create();
//		conf.addResource(new Path("/home/hadoop/conf/hbase", "hbase-site.xml"));
		conf.addResource(new Path("/home/hadoop/hbase-1.0.0-cdh5.4.5/conf", "hbase-site.xml"));
		//conf.addResource(new Path("/Users/liaoliuqing/Downloads/conf_loghbase/hbase", "hbase-site.xml"));
		if (args.length > 0) {
			conf.set("hadoop.security.authentication", "Kerberos");
			UserGroupInformation.setConfiguration(conf);
			UserGroupInformation.loginUserFromKeytab("/home/hadoop/keytab/formal.keytab", "hbase/hmaster");
		}

		Connection connection = ConnectionFactory.createConnection(conf);
		Admin admin = connection.getAdmin();
//		Table table = connection.getTable(TableName.valueOf("udid_profile_sdc_g18"));
//		Scan sc = new Scan();
//		ResultScanner rs = table.getScanner(sc);
//		System.out.println(rs.next());
		
		
	
		System.out.println(admin.tableExists(TableName.valueOf("udid_profile_sdc_l10")));
		admin.close();
		
		connection.close();

		// Configuration configuration = HBaseConfiguration.create();
		// // configuration.set("hbase.zookeeper.quorum",
		// "formalhbase.zk.gdc.x.netease.com");
		// //configuration.set("hbase.rpc.timeout", "60000");
		// configuration.addResource(new Path("/home/hadoop/conf/hbase",
		// "hbase-site.xml"));
		// configuration.set("hadoop.security.authentication", "Kerberos");
		//
		// UserGroupInformation.setConfiguration(configuration);
		// UserGroupInformation.loginUserFromKeytab("/home/hadoop/keytab/formal.keytab",
		// "hbase/hmaster");
		// HBaseAdmin hbaseAdmin = new HBaseAdmin(configuration);
		// String tableName = "udid_profile_sdc_g18";
		// System.out.println(hbaseAdmin.tableExists(tableName));
		// hbaseAdmin.close();

	}

}
