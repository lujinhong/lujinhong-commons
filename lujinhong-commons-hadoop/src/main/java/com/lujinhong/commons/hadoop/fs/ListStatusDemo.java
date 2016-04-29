package com.lujinhong.commons.hadoop.fs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

public class ListStatusDemo {

	public static void main(String[] args) throws IOException {
		
		String dir = args[0];
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dir), conf);
		FileStatus[] stats =  fs.listStatus(new Path(dir));
		
		Path[] paths = FileUtil.stat2Paths(stats);
		for(Path path : paths){
			System.out.println(path);
		}
	}

}
