package com.lujinhong.commons.hadoop.fs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileStatusDemo {

	public static void main(String[] args) throws IOException {
		
		String fileName = args[0];
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(fileName), conf);
		
		FileStatus status = fs.getFileStatus(new Path(fileName));
		System.out.println(status.getOwner()+" "+status.getModificationTime());
		
	
	}

}
