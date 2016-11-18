package com.lujinhong.commons.java.scheduler;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
* date: 2016年9月9日 下午3:38:37
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年9月9日 下午3:38:37
*/

public class ScheduledThreadPoolExecutorDemo {

	public static void main(String[] args) throws InterruptedException {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);
		PrintTime task = new PrintTime();
		//2秒后开始执行task，只执行一次。
		//executor.schedule(task, 2, TimeUnit.SECONDS);
		//1秒后开始执行task,每秒执行一次
		executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
		//1秒后开始执行task，在一次任务执行完成后1秒再重复执行task，如此循环。
		//executor.scheduleWithFixedDelay(task, 1, 1, TimeUnit.SECONDS);
		Thread.sleep(10000);
		executor.shutdown();
	}
}

 class PrintTime implements Runnable{
	@Override
	public void run() {
		System.out.println(System.currentTimeMillis());
	}
}