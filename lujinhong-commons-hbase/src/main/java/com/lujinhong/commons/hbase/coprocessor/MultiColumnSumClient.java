package com.lujinhong.commons.hbase.coprocessor;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.ipc.BlockingRpcCallback;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * date: 2016年5月27日 上午11:09:19
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年5月27日 上午11:09:19
 */

public class MultiColumnSumClient {

	private static final String tableName = "endpoint_test";

	public static class ResponseInfo {
		public long count1;
		public long count2;
		public long sum1;
		public long sum2;

	}

	public static void main(String[] args) throws Throwable {
		Configuration conf = HBaseConfiguration.create();
		conf.addResource(new Path("/home/hadoop/conf/hbase", "hbase-site.xml"));
		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf(Bytes.toBytes(tableName)));

		final MultiColumnSumProtocol.CountRequest request = MultiColumnSumProtocol.CountRequest.newBuilder()
				.setColumns("c1;c2").build();

		Map<byte[], ResponseInfo> map = table.coprocessorService(MultiColumnSumProtocol.RowCountService.class, null,
				null, new Batch.Call<MultiColumnSumProtocol.RowCountService, ResponseInfo>() {

					@Override
					public ResponseInfo call(MultiColumnSumProtocol.RowCountService service) throws IOException {
						BlockingRpcCallback<MultiColumnSumProtocol.CountResponse> rpcCallback = new BlockingRpcCallback<>();
						service.getCountAndSum(null, request, rpcCallback);
						MultiColumnSumProtocol.CountResponse response = rpcCallback.get();
						//直接返回response也行。
						ResponseInfo responseInfo = new ResponseInfo();
						responseInfo.count1 = response.getCount1();
						responseInfo.count2 = response.getCount2();
						responseInfo.sum1 = response.getSum1();
						responseInfo.sum2 = response.getSum2();
						return responseInfo;
					}
				});

		ResponseInfo result = new ResponseInfo();
		for (ResponseInfo ri : map.values()) {
			result.count1 += ri.count1;
			result.count2 += ri.count2;
			result.sum1 += ri.sum1;
			result.sum2 += ri.sum2;
		}

		System.out.println("Produce 1 has " + result.count1 + " user, all online time is " + result.sum1 / 1000
				+ " minutes, average online time is " + result.sum1 / 1000 / result.count1 + "minutes.");
		
		System.out.println("Produce 2 has " + result.count2 + " user, all online time is " + result.sum2 / 1000
				+ " minutes, average online time is " + result.sum2 / 1000 / result.count2 + "minutes.");
		

	}

}

