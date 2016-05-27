package com.lujinhong.commons.hbase.coprocessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.Coprocessor;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.coprocessor.CoprocessorException;
import org.apache.hadoop.hbase.coprocessor.CoprocessorService;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.protobuf.ResponseConverter;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.util.Bytes;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;
import com.lujinhong.commons.hbase.coprocessor.MultiColumnSumProtocol.CountRequest;
import com.lujinhong.commons.hbase.coprocessor.MultiColumnSumProtocol.CountResponse;

/**
 * date: 2016年5月27日 上午10:20:48
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年5月27日 上午10:20:48
 */

public class MultiColumnSum extends MultiColumnSumProtocol.RowCountService implements Coprocessor, CoprocessorService {

	private RegionCoprocessorEnvironment env;
	private static final String FAMILY = "f1";

	@Override
	public Service getService() {
		return this;
	}

	@Override
	public void start(CoprocessorEnvironment env) throws IOException {
		if (env instanceof RegionCoprocessorEnvironment) {
			this.env = (RegionCoprocessorEnvironment) env;
		} else {
			throw new CoprocessorException("Must be loaded on a table region!");
		}
	}

	@Override
	public void stop(CoprocessorEnvironment env) throws IOException {
	}

	@Override
	public void getCountAndSum(RpcController controller, CountRequest request, RpcCallback<CountResponse> done) {
		long[] values = { 0, 0, 0, 0 };
		String columns = request.getColumns();
		if (columns == null || "".equals(columns))
			throw new NullPointerException("you need specify the columns");
		String[] columnArray = columns.split(";");

		Scan scan = new Scan();

		for (String column : columnArray) {
			scan.addColumn(Bytes.toBytes(FAMILY), Bytes.toBytes(column));
		}

		MultiColumnSumProtocol.CountResponse response = null;
		InternalScanner scanner = null;
		try {
			scanner = env.getRegion().getScanner(scan);
			List<Cell> results = new ArrayList<Cell>();
			boolean hasMore = false;
			do {
				hasMore = scanner.next(results);
				if (results.size() < 2)
					continue;
				Cell kv0 = results.get(0);
				long value1 = Long.parseLong(Bytes.toString(CellUtil.cloneValue(kv0)));
				Cell kv1 = results.get(1);
				long value2 = Long.parseLong(Bytes.toString(CellUtil.cloneValue(kv1)));
				if (value1 > 60000) {
					values[0] += 1;
					values[2] += value1;
				}
				if (value2 > 60000) {
					values[1] += 1;
					values[3] += value2;
				}

				results.clear();
			} while (hasMore);

			// 生成response
			response = MultiColumnSumProtocol.CountResponse.newBuilder().setCount1(values[0]).setCount2(values[1])
					.setSum1(values[2]).setSum2(values[3]).build();

		} catch (IOException e) {
			e.printStackTrace();
			ResponseConverter.setControllerException(controller, e);
		} finally {
			if (scanner != null) {
				try {
					scanner.close();
				} catch (IOException ignored) {
				}
			}
		}
		done.run(response);
	}

}
