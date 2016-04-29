/**
 * 
 */
package com.lujinhong.commons.java.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * date: 2016年4月26日 下午2:44:23
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年4月26日 下午2:44:23
 */

public class BasicIODemo {
	private static int content = 1234;

	public static void main(String[] args) throws IOException {

		// 向文件中写入二进制数据
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("io.txt", true)));
		dos.writeInt(content);
		dos.close();

		// 读取文件中的二进制数据
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("io.txt")));
		while (dis.available() != 0) {
			System.out.println(dis.readInt());
		}
		dis.close();

		// 向文本中写入字符
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("io2.txt", true)));
		pw.write(content + "");
		pw.close();

		// 读取文本中的字符
		Scanner sc = new Scanner(new BufferedReader(new FileReader("io2.txt")));
		while (sc.hasNext()) {
			System.out.println(sc.nextLine());
		}
		sc.close();
	}

}
