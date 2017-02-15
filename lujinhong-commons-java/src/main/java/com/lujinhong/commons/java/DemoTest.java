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
	private static final String HIVE_DEFAULT_SEPARATOR = "\\u0001";

	public static void main(String[] args) throws Exception{
		String s = "3.5205001926673745E-5\u00015.194886154069933E-6";
		System.out.println(s.split(HIVE_DEFAULT_SEPARATOR)[1]);
	}
	
	
	private static String getMonitorInformation(String message) throws JSONException {
		String time = message.substring(message.indexOf("[20")).substring(1, 17).replace("-", "").replace(" ", "").replace(":", "");
		
		String jsonString = message.substring(message.indexOf("{"));
		JSONObject messageObject = new JSONObject(jsonString);
		String adx = messageObject.getString("adx");
		
		return time + ":" + adx;

	}

}
