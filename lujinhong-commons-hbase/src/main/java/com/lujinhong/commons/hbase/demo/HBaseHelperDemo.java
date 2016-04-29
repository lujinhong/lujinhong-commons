/**
 * 
 */
package com.lujinhong.commons.hbase.demo;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.lujinhong.commons.hbase.utils.HBaseHelper;

/**
 * date: 2016年4月15日 下午2:41:20
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年4月15日 下午2:41:20
 */

//示范了如何使用HBaseHelper中的相关方法。
public class HBaseHelperDemo {
	
	private static String[] rows = new String[]{"r1","r2","r3"};
	private static String[] vals = new String[]{"v1","v2","v3"};

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.addResource(new Path("/Users/liaoliuqing/Downloads/conf_loghbase/hbase", "hbase-site.xml"));
		HBaseHelper helper = HBaseHelper.getHelper(conf);
		if(helper.existsTable("ljhtest3")){
			helper.dropTable("ljhtest3");
		}
		helper.createTable("ljhtest3", "f1");
		//put一条数据
		helper.put("ljhtest3", "r2", "f1", "c2", "value3");
		//put一系列数据
		helper.put("ljhtest3", rows, "f1", "c1", vals);
		
		//get 一条数据
		Result r = helper.get("ljhtest3", "r1", "f1", "c1");
		System.out.println(r.toString());
		//get 一批数据
		Result[] results = helper.get("ljhtest3", rows, "f1", "c1");
		for (Result result : results) {
			for (Cell cell : result.rawCells()) {
				System.out.println("Cell: " + cell + ", Value: "
						+ Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
			}
		}
		
		helper.dump("ljhtest");
		
		helper.close();
	}

}
