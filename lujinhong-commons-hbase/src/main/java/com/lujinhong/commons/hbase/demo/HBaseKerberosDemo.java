/**
 * 
 */
package com.lujinhong.commons.hbase.demo;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;

/**
* date: 2016年4月22日 下午4:19:30
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO ADD FUNCTION.
* last modified: 2016年4月22日 下午4:19:30
*/

public class HBaseKerberosDemo {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		conf.set("hadoop.security.authentication", "Kerberos");
		
		UserGroupInformation.setConfiguration(conf);
		UserGroupInformation.loginUserFromKeytab(args[0], args[1]);
		
		Connection connection = ConnectionFactory.createConnection(conf);
		
		Table tbl = connection.getTable(TableName.valueOf(args[2]));
	    Put put = new Put(Bytes.toBytes("r1"));
	    put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("c1"), Bytes.toBytes("v1"));
	    tbl.put(put);
	    tbl.close();
	    connection.close();

	}

}
