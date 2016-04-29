package com.lujinhong.commons.java.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarDemo {

	public static void main(String[] args) throws ParseException {
		
		//1、获取当前时间，并打印
		Calendar calendar = Calendar.getInstance(); 
		System.out.println(calendar.get(Calendar.YEAR) +"" + calendar.get(Calendar.MONTH)+ calendar.get(Calendar.DAY_OF_MONTH)+ calendar.get(Calendar.HOUR_OF_DAY)+ calendar.get(Calendar.MINUTE)+  calendar.get(Calendar.SECOND));
		
		
		//2、使用指定格式生成一个Calendar对象。
		Date date = null;
		date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-01-01 01:01:01");
		calendar.setTime(date);
		System.out.println(calendar.get(Calendar.YEAR) +"" + calendar.get(Calendar.MONTH)+ calendar.get(Calendar.DAY_OF_MONTH)+ calendar.get(Calendar.HOUR_OF_DAY)+ calendar.get(Calendar.MINUTE)+  calendar.get(Calendar.SECOND));

	}

}

