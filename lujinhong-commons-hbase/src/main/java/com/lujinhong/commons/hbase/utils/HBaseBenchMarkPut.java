/**
 * 
 */
package com.lujinhong.commons.hbase.utils;

import java.io.IOException;
import java.util.Map;
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

public class HBaseBenchMarkPut {

	private static Logger LOG = LoggerFactory.getLogger(HBaseBenchMarkPut.class);
	private static final int[] SIZES = new int[] { 10000, 100000, 300000 };
	private static final int TEST_TIMES = 5;
	private static final String TABLENAME = "ljhtest";
	private static final TreeMap<Integer, Double> timeMap = new TreeMap<Integer, Double>();

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.addResource(new Path("/Users/liaoliuqing/Downloads/conf_loghbase/hbase", "hbase-site.xml"));
		HBaseHelper helper = HBaseHelper.getHelper(conf);

		// for (int j = 0; j < TEST_TIMES; j++) {
		for (int SIZE : SIZES) {
			if (helper.existsTable(TABLENAME)) {
				helper.dropTable(TABLENAME);
			}

			byte[][] splits = new byte[][] { Bytes.toBytes("row" + SIZE / 6 + "id"),
					Bytes.toBytes("row" + SIZE / 6 * 2 + "id"), Bytes.toBytes("row" + SIZE / 6 * 3 + "id"),
					Bytes.toBytes("row" + SIZE / 6 * 4 + "id"), Bytes.toBytes("row" + SIZE / 6 * 5 + "id") };

			helper.createTable(TABLENAME, splits, "f1");

			String[] rows = new String[SIZE];
			String[] values = new String[SIZE];
			for (int i = 0; i < SIZE; i++) {
				rows[i] = "r" + i;
				values[i] = "v" + i;
			}
			double time = put(helper, TABLENAME, rows, values, SIZE);
			if (timeMap.get(SIZE) == null) {
				timeMap.put(SIZE, 0.0);
			}
			timeMap.put(SIZE, time);

		}
		// }

		System.out.println("size\ttime");
		for (Map.Entry<Integer, Double> entry : timeMap.entrySet()) {
			System.out.println(entry.getKey() + " \t" + entry.getValue());
		}

		helper.close();
	}

	/**
	 * @param helper
	 * @param rows
	 * @param values
	 * @throws IOException
	 */
	private static double put(HBaseHelper helper, String tableName, String[] rows, String[] values, int SIZE)
			throws IOException {
		Long beginTime = System.currentTimeMillis();
		System.out.println("Begin putting data at  " + beginTime);
		helper.put(tableName, rows, "f1", "c1", values);
		Long finishTime = System.currentTimeMillis();
		System.out.println("Finish putting data " + finishTime);

		double ret = (finishTime - beginTime) / 1000.0;
		System.out.println("Putting " + SIZE + " rows in " + ret + " seconds.");
		return ret;
	}

}
