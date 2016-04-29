package com.lujinhong.commons.hadoop.fs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class FileCopy {

	public static void main(String[] args) throws IOException {
		String sourceFile = args[0];
		String destFile = args[1];

		InputStream in = null;
		OutputStream out = null;
		try {
			//1､准备输入流
			in = new BufferedInputStream(new FileInputStream(sourceFile));
			//2､准备输出流
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(URI.create(destFile), conf);
			out = fs.create(new Path(destFile));
			//3､复制
			IOUtils.copyBytes(in, out, 4096, false);
		} finally {
			in.close();
			out.close();
		}

	}

}
