package com.lujinhong.commons.hadoop.fs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class FileSystemDoubleCat {

	public static void main(String[] args) throws IOException {

		String fileName = args[0];
		Configuration conf = new Configuration();

		FileSystem fs = FileSystem.get(URI.create(fileName), conf);
		FSDataInputStream in = null;
		try {
			in = fs.open(new Path(fileName));
			IOUtils.copyBytes(in, System.out, 4096, false);
			in.seek(0);
			IOUtils.copyBytes(in, System.out, 4096, false);
		} finally {
			in.close();
		}

	}

}
