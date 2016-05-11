/**
 * 本类用于示范如何创建一个新的线程
 */
package com.lujinhong.commons.java.multithread;

import java.util.Date;

/**
 * @author lujinhong
 * @date 2015年6月21日 下午10:42:29
 * @Description:
 */
public class CreateThread {

	public static void main(String[] args) {
		//2、使用线程类创建一个线程对象
		PrintDate pd = new PrintDate();
		Thread t = new Thread(pd);
		//3、启动线程
		t.start();
	}
}
//1、创建线程类
class PrintDate implements Runnable {

	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(new Date());

		}
	}
}
