package com.lujinhong.commons.java.ip;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * date: 2016年11月2日 下午12:51:06
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年11月2日 下午12:51:06
 */

public class LinkFileExistDemo {

	private static final Logger LOG = LoggerFactory.getLogger(LinkFileExistDemo.class);

	public static void main(String[] args) {

		File file1 = new File("/Users/liaoliuqing/Downloads/1.lnk");
		File file2 = new File("/Users/liaoliuqing/Downloads/2.lnk");

		if (Files.exists(file1.toPath())) {
			LOG.info("File {} is exist.", file1.getPath());
		} else {
			LOG.info("File {} is  not exist.", file1.getPath());
		}

	}

}
