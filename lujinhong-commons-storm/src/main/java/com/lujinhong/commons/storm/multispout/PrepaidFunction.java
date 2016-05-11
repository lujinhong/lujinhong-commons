/**
 * 
 */
package com.lujinhong.commons.storm.multispout;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * date: 2016年3月15日 下午2:17:48
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年3月15日 下午2:17:48
 */

public class PrepaidFunction extends BaseFunction {

	private static Logger log = LoggerFactory.getLogger(PrepaidFunction.class);

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String message = tuple.get(0).toString();
		try {
			if (message.contains("[Prepaid]")) {
				String jsonText = getJsonText(message);
				JSONObject jsonObject = new JSONObject(jsonText);
//				if (message.contains("g17")) {
//					log.info("g17");
//				} else {
//					log.info("g18");
//				}
				log.info(jsonObject.getString("server") + "\t" + jsonObject.get("cash").toString());
				collector.emit(new Values(jsonObject.getString("server"), jsonObject.get("cash").toString()));
			}else if(tuple.get(0).toString().contains("$$$TIMERMESSAGE$$$")){
				//DO something according to the timer.
				log.info("receive timer=============================at " + Calendar.getInstance().getTime());
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
