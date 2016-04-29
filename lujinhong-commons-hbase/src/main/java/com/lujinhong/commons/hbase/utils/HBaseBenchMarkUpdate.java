/**
 * 
 */
package com.lujinhong.commons.hbase.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * date: 2016年4月16日 下午7:42:37
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年4月16日 下午7:42:37
 */

public class HBaseBenchMarkUpdate {

	private static Logger LOG = LoggerFactory.getLogger(HBaseBenchMarkUpdate.class);
	private static final int TABLE_SIZE = 50000000;
	private static final int UPDATE_SIZE = 100000;

	private static final String TABLENAME = "benchmark_update";

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.addResource(new Path("/Users/liaoliuqing/Downloads/conf_loghbase/hbase", "hbase-site.xml"));
		HBaseHelper helper = HBaseHelper.getHelper(conf);

		if (!helper.existsTable(TABLENAME)) {
			prepareTable(helper);
		}
		Random random = new Random(50000000);
		String[] rows = new String[UPDATE_SIZE];
		String[] values = new String[UPDATE_SIZE];
		
		for(int i = 0; i< UPDATE_SIZE; i++){
			int key = Math.abs(random.nextInt());
			rows[i] = "row" + key + i + "id";
			values[i] = "value" + key + i
					+ "newvalue_longsizevalue_longsizevalue_longsizevalue_longsizevalue_longsize";
		}
		
		Long beginTime = System.currentTimeMillis();
		System.out.println("Begin update data at  " + beginTime);
		
		helper.put(TABLENAME, rows, "f1", "c1", values);
		
		Long finishTime = System.currentTimeMillis();
		System.out.println("Finish update data " + finishTime);
		double ret = (finishTime - beginTime) / 1000.0;
		System.out.println("Updating " + UPDATE_SIZE + " rows in " + ret + " seconds.");

		helper.close();
	}

	/**
	 * @param helper
	 * @param rows
	 * @param values
	 * @throws IOException
	 */
	private static void prepareTable(HBaseHelper helper) throws IOException {


		byte[][] splits = new byte[][] { Bytes.toBytes("row" + TABLE_SIZE / 6 + "id"),
				Bytes.toBytes("row" + TABLE_SIZE / 6 * 2 + "id"), Bytes.toBytes("row" + TABLE_SIZE / 6 * 3 + "id"),
				Bytes.toBytes("row" + TABLE_SIZE / 6 * 4 + "id"), Bytes.toBytes("row" + TABLE_SIZE / 6 * 5 + "id") };

		helper.createTable(TABLENAME, splits, "f1");

		int perBatch = 1000000;
		for (int beginIndex = perBatch; beginIndex < TABLE_SIZE; beginIndex += perBatch) {
			String[] rows = new String[perBatch];
			String[] values = new String[perBatch];
			for (int i = 0; i < perBatch; i++) {
				rows[i] = "row" + beginIndex + i + "id";
				values[i] = "value" + beginIndex + i
						+ "value_longsizevalue_longsizevalue_longsizevalue_longsizevalue_longsize";
			}
			Long beginTime = System.currentTimeMillis();
			System.out.println("Begin putting data at  " + beginTime);
			helper.put(TABLENAME, rows, "f1", "c1", values);
			Long finishTime = System.currentTimeMillis();
			System.out.println("Finish putting data " + finishTime);

		}
	}

}
