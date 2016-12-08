package com.lujinhong.commons.storm.drpc;

import org.apache.thrift7.transport.TTransportException;

import backtype.storm.Config;
import backtype.storm.utils.DRPCClient;

/**
 * date: 2016年7月4日 下午2:20:16
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified: 2016年7月4日
 *         下午2:20:16
 */

public class DRPCHbaseGetterClient {

	public static void main(String[] args) throws Exception {

		Config conf = new Config();
		conf.setDebug(false);
		conf.put("storm.thrift.transport", "backtype.storm.security.auth.SimpleTransportPlugin");
		conf.put(Config.STORM_NIMBUS_RETRY_TIMES, 3);
		conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL, 10);
		conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL_CEILING, 20);
		conf.put(Config.DRPC_MAX_BUFFER_SIZE, 1048576);

		DRPCClient client = new DRPCClient(conf, "nimbus.ip_or_host", 3772);

		String rowkey = args[0];
		// 返回结果是一个KV格式，KEY为请求的id, V为最后一个bolt的返回结果。被封装成一个2个值的tuple。
		System.out.println(client.execute("hbase-getter", rowkey));

	}

}
