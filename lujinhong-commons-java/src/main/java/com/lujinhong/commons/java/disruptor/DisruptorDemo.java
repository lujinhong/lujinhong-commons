package com.lujinhong.commons.java.disruptor;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * date: 2016年7月5日 下午5:29:34
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified: 2016年7月5日
 *         下午5:29:34
 */

public class DisruptorDemo {
	private static Random random = new Random();

	public static void main(String[] args) {

		// 一、启动disruptor，并指定处理事件的handler
		EventFactory<LongEvent> eventFactory = new LongEventFactory();
		ExecutorService executor = Executors.newSingleThreadExecutor();
		int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；

		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory, ringBufferSize, executor,
				ProducerType.SINGLE, new YieldingWaitStrategy());

		//指定使用哪个handler来处理事件
		EventHandler<LongEvent> eventHandler = new LongEventHandler();
		disruptor.handleEventsWith(eventHandler);

		disruptor.start();

		// 二、向Disruptor中发布事件

		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		for (int i = 0; i < 100; i++) {
			long sequence = ringBuffer.next();// 请求下一个事件序号；

			try {
				LongEvent event = ringBuffer.get(sequence);// 获取该序号对应的事件对象；
				long data = getEventData();// 获取要通过事件传递的业务数据；
				event.set(data);
			} finally {
				ringBuffer.publish(sequence);// 发布事件；
			}
		}

		disruptor.shutdown();// 关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
		executor.shutdown();// 关闭 disruptor 使用的线程池；如果需要的话，必须手动关闭， disruptor 在
							// shutdown 时不会自动关闭；

	}

	private static long getEventData() {
		return random.nextLong();
	}

}
