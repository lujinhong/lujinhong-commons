package com.lujinhong.commons.hadoop.fs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class DeleteFileDemo {

	public static void main(String[] args) throws IOException {

		String fileName = args[0];

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(fileName), conf);

		try {
			fs.delete(new Path("/tmp/file_to_delte"), true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
