/**
 * 
 */
package com.lujinhong.commons.java.nio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * date: 2016年5月4日 上午10:37:48
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年5月4日 上午10:37:48
 */

public class BasicDemo {

	private static final String fileName = "/Users/liaoliuqing/Downloads/1.txt";
	private static final Path path = Paths.get(fileName);

	public static void main(String[] args) throws IOException {

		// 向文件中写入内容
		Charset charset = Charset.forName("utf-8");
		try (BufferedWriter writer = Files.newBufferedWriter(path, charset, StandardOpenOption.APPEND,
				StandardOpenOption.CREATE)) {
			writer.write("hello world\n");
			writer.write("中文");
		}

		// 读取文件内容
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {

			String line = null;
			do {
				line = reader.readLine();
				if (line != null)
					System.out.println(line);
			} while (line != null);
		}
	}
}
