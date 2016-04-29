package com.lujinhong.commons.java.slf4j;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jinhong-lu
 * @date 2015年7月9日 下午4:34:49
 * @Description:
 */
public class Slf4jDemo {
	private static final Logger LOG = LoggerFactory.getLogger(Slf4jDemo.class);

	public static void main(String[] args) {


		// 使用此变量会生成文件成功
		String fileName = "1.txt";
		// 使用此变量会生成文件失败
		// String fileName = "/tt/1.txt";
		try {
			new File(fileName).createNewFile();
			LOG.info("create file " + fileName + "!");
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("create file " + fileName + " fail!!!!" + e.getMessage());
		}

	}

}
