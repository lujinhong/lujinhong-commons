/**
 * 
 */
package com.lujinhong.commons.storm.kinit;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

/**
 * date: 2016年3月15日 下午2:17:48
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年3月15日 下午2:17:48
 */

public class PrepaidFunction extends BaseFunction {

	private static Logger log = LoggerFactory.getLogger(PrepaidFunction.class);
	
	HBaseHelper helper =  null ;



	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		Configuration config = HBaseConfiguration.create();
		config.addResource(new Path("/home/hadoop/conf/hbase", "hbase-site.xml"));
		config.set("hadoop.security.authentication", "Kerberos");
		UserGroupInformation.setConfiguration(config);
		
		try {
			UserGroupInformation.loginUserFromKeytab("hbase/hmaster", "/home/hadoop/keytab/formal.keytab");
			helper = HBaseHelper.getHelper(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(System.getProperty("java.class.path"));
		
		super.prepare(conf, context);
	}
	
	@Override
	public void cleanup() {
		try {
			helper.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.cleanup();
	}

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String message = tuple.getString(0);
		try {
			if (message.contains("[Prepaid]")) {
				log.info("Preparid");
				String jsonText = getJsonText(message);
				JSONObject jsonObject = new JSONObject(jsonText);

				//log.info(jsonObject.getString("server") + "\t" + jsonObject.getInt("cash"));

				try {
					//TODO: modify to mutliUpdate
					//Result r = helper.get("ljhtest", jsonObject.getString("server"), "f1", "cash");
					//int nowCash = Integer.parseInt(Bytes.toString(r.getValue(Bytes.toBytes("f1"), Bytes.toBytes("cash"))));
					helper.put("ljhtest", "server", "f1", "cash",  "100");
				} catch (IOException e) {
					e.printStackTrace();
				}
				//collector.emit(new Values(jsonObject.getString("server"), jsonObject.getInt("cash")));
			}
		} catch (JSONException e) {
			log.error("Error when handling :" + message);
			e.printStackTrace();
		}

	}

	private String getJsonText(String message) {
		return message.substring(message.indexOf("{"));
	}
	

}
