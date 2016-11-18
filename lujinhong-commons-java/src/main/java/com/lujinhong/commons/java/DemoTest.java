package com.lujinhong.commons.java;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

/**
* date: 2016年6月27日 上午10:21:22
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年6月27日 上午10:21:22
*/

public class DemoTest {
	
	
	public static void main(String[] args) throws Exception{

		String message = "/home/edt/log/drpf.log [2016-09-22 10:01:14 +0800][adx_req]{\"id\":\"6128267-75040\",\"adx\":\"iqiyi\"}";
		System.out.println(getMonitorInformation(message));
		
        //new Timer().scheduleAtFixedRate(task, 0, TimeUnit.HOURS.toMillis(purgeInterval));
        System.out.println(TimeUnit.HOURS.toMillis(1));
        System.out.println(TimeUnit.MINUTES.toMillis(1));
	}
	
	
	private static String getMonitorInformation(String message) throws JSONException {
		String time = message.substring(message.indexOf("[20")).substring(1, 17).replace("-", "").replace(" ", "").replace(":", "");
		
		String jsonString = message.substring(message.indexOf("{"));
		JSONObject messageObject = new JSONObject(jsonString);
		String adx = messageObject.getString("adx");
		
		return time + ":" + adx;

	}

}
