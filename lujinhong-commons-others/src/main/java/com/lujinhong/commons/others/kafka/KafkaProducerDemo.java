package com.lujinhong.commons.others.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * date: 2016年11月1日 上午10:50:55
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年11月1日 上午10:50:55
 */

public class KafkaProducerDemo {

	public static void main(String[] args) {
		prodcerDemo1();
		prodcerDemo2();
		prodcerDemo3();
	}

	// 最简单的producer，所有配置使用默认值。
	private static void prodcerDemo1() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.1.1.1:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 100; i++)
			producer.send(new ProducerRecord<String, String>("ljhtest", "producerDemo1：" + i));

		producer.close();
	}

	// 强制同步发送，准确性最高，吞吐量较低。
	private static void prodcerDemo2() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.1.1.1:9092");
		props.put("batch.size", 1);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 100; i++)
			producer.send(new ProducerRecord<String, String>("ljhtest", "producerDemo2：" + i));

		producer.close();
	}

	// 异步发送，较高的吞吐量。极端情况下有可能存在部分发送成功，部分失败的情况。此时根据业务逻辑可以选择忽略（丢失部分数据），或者重发（重复部分数据）
	private static void prodcerDemo3() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.1.1.1:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 10);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 100; i++)
			producer.send(new ProducerRecord<String, String>("ljhtest", "producerDemo3：" + i));

		producer.close();
	}

}
