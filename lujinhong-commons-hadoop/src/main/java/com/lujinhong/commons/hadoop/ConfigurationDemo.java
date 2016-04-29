package com.lujinhong.commons.hadoop;

import org.apache.hadoop.conf.Configuration;

public class ConfigurationDemo {
	public static void main(String[] args){
		Configuration conf = new Configuration();
		Configuration.addDefaultResource("/Users/liaoliuqing/Downloads/color2.xml");
		//Configuration.addDefaultResource("color.xml");
		Configuration.addDefaultResource("mapred-default.xml");
		
		System.out.println(conf.get("fs.default.name"));
		System.out.println(conf.get("hadoop.tmp.dir"));
		System.out.println(conf.get("io.sort.mb"));
		
		System.out.println(conf.get("color"));		
	}
}
