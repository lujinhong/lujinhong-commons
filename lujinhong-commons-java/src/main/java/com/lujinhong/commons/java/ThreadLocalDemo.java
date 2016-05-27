package com.lujinhong.commons.java;

/**
 * date: 2016年5月12日 下午2:21:32
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年5月12日 下午2:21:32
 */

public class ThreadLocalDemo {

	public static void main(String[] args) throws InterruptedException {
		//如果使用ThreadLocal，则只需要一个Runnable对象
		Runnable shareRunnableInstance = new MyRunnable();
		Thread t1 = new Thread(shareRunnableInstance);
		Thread t2 = new Thread(shareRunnableInstance);
		t1.start();
		Thread.sleep(5000);
		t2.start();

		//否则需要2个
		Runnable r1 = new MyRunnable2();
		Runnable r2 = new MyRunnable2();
		Thread t3 = new Thread(r1);
		Thread t4 = new Thread(r2);
		t3.start();
		Thread.sleep(5000);
		t4.start();
	}

}

class MyRunnable implements Runnable {
	private ThreadLocal<Long> threadCreateTime = new ThreadLocal<Long>() {
		@Override
		protected Long initialValue() {
			return 0L;
		}
	};

	@Override
	public void run() {
		threadCreateTime.set(System.currentTimeMillis());
		System.out.println(threadCreateTime.get());
	}

}

class MyRunnable2 implements Runnable {
	private Long threadCreateTime = 0L;

	@Override
	public void run() {
		threadCreateTime = System.currentTimeMillis();
		System.out.println(threadCreateTime);
	}

}
