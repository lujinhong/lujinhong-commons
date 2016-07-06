package com.lujinhong.commons.java.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * date: 2016年6月14日 上午9:59:03
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年6月14日 上午9:59:03
 */

public class SymbolicDemo {
	public static void main(String[] args) throws IOException, InterruptedException {

		for (int i = 0; i < 50; i++) {
			File f2 = new File("/Users/liaoliuqing/Downloads/1.current");
			if (f2.exists()) {
				Files.delete(f2.toPath());
				System.out.println("deleting " + f2.getPath());
			}
			Files.createSymbolicLink(f2.toPath(), Paths.get("/Users/liaoliuqing/Downloads/1.java"));
			System.out.println("create symbolicLink");
			Thread.sleep(2000);

		}

	}

}
