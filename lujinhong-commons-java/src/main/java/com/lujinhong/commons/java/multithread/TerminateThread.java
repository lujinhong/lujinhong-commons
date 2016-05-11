package com.lujinhong.commons.java.multithread;

import java.util.Calendar;

/**
 * date: 2016年5月11日 下午3:11:34
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: 展示了终止线程的2种方法 last modified:
 *         2016年5月11日 下午3:11:34
 */

public class TerminateThread {

	public static void main(String[] args) throws InterruptedException {
		//1、使用interrupt()方法终止线程
		Runner runnerOne = new Runner();
		Thread threadOne = new Thread(runnerOne);
		threadOne.start();
		Thread.sleep(5000);
		threadOne.interrupt();
		
		//2、使用boolean对象终止线程
		Runner runnerTwo = new Runner();
		Thread threadTwo = new Thread(runnerTwo);
		threadTwo.start();
		Thread.sleep(5000);
		runnerTwo.cancle();
	}

}

class Runner implements Runnable {
	private static boolean on = true;

	@Override
	public void run() {
		while (on && !Thread.currentThread().isInterrupted()) {
			System.out.println("Current time is " + Calendar.getInstance().getTime());
		}
	}

	public void cancle() {
		on = false;
	}

}
