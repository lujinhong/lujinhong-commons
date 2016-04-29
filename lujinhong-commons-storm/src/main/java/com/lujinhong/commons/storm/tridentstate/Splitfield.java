package com.lujinhong.commons.storm.tridentstate;

import org.apache.commons.lang.exception.ExceptionUtils;

import backtype.storm.tuple.Values;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class Splitfield extends BaseFunction {


	private static final long serialVersionUID = 8428147000997859495L;
	@Override
	public void execute(TridentTuple input, TridentCollector collector) {
		String source=input.getString(0);
		try{
			JSONObject object= JSON.parseObject(source.toLowerCase());
			
		  String name=object.getString("name");
		  int age=object.getInteger("age");
		  String title=object.getString("title");
		  String tel=object.getString("tel");
			
			collector.emit(new Values(name,age,title,tel));
			
			System.out.println("i am in Splitfield.java Thread.Id="+Thread.currentThread().getId());

			
			
		}catch(Exception e){
			System.out.println("Splitjava ="+ExceptionUtils.getFullStackTrace(e));;
		}

}






}