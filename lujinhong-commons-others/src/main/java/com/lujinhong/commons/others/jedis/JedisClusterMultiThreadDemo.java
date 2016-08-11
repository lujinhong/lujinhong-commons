package com.lujinhong.commons.others.jedis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * date: 2016年7月19日 上午11:07:09
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年7月19日 上午11:07:09
 */

public class JedisClusterMultiThreadDemo {
	private static ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<String>();
	private static int num = 10000;

	public static void main(String[] args) throws IOException, InterruptedException {

		int threadCount = 1;
		if (args.length > 0) {
			num = Integer.parseInt(args[0]);
		}
		if (args.length > 1) {
			threadCount = Integer.parseInt(args[1]);
		}
		//init();

		for (int i = 0; i < threadCount; i++) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(3);
					config.setMaxIdle(2);

					HostAndPort hp0 = new HostAndPort("ip1", 6379);
					HostAndPort hp1 = new HostAndPort("ip2", 6379);
					HostAndPort hp2 = new HostAndPort("ip3", 6379);
					HostAndPort hp3 = new HostAndPort("ip4", 6379);
					HostAndPort hp4 = new HostAndPort("ip5", 6379);
					HostAndPort hp5 = new HostAndPort("ip6", 6379);

					Set<HostAndPort> hps = new HashSet<HostAndPort>();
					hps.add(hp0);
					hps.add(hp1);
					hps.add(hp2);
					hps.add(hp3);
					hps.add(hp4);
					hps.add(hp5);

					// 超时，最大的转发数，最大链接数，最小链接数都会影响到集群
					JedisCluster jedisCluster = new JedisCluster(hps, 5000, 10, config);

					// test set
					long start = System.currentTimeMillis();
					for(int i=0;i<num; i++){
						jedisCluster.set("key" +i, "value");
						//System.out.println("setting " + i);
					}
					long end = System.currentTimeMillis();
					System.out.println("It takes " + (end - start) / 1000.0 + " seconds to put " + num + " data.");

					// //test get
					// start = System.currentTimeMillis();
					// for (int i = 0; i < num; i++) {
					// jedisCluster.get(list.poll());
					// }
					// end = System.currentTimeMillis();
					// System.out.println("It takes " + (end - start) / 1000.0 +
					// " seconds to get " + num + " data.");
					try {
						jedisCluster.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			});
			thread.start();


		}
		
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(3);
//		config.setMaxIdle(2);
//
//
//		Set<HostAndPort> hps = new HashSet<HostAndPort>();
//		hps.add(hp0);
//		hps.add(hp1);
//		hps.add(hp2);
//		hps.add(hp3);
//		hps.add(hp4);
//		hps.add(hp5);
//
//		// 超时，最大的转发数，最大链接数，最小链接数都会影响到集群
//		JedisCluster jedisCluster = new JedisCluster(hps, 5000, 10, config);
//		String[] keys = new String[1];
//		for(int j =0; j< 1; j++){
//			keys[j] = "key" +j;
//		}
//		long t1 = System.currentTimeMillis();
//		jedisCluster.mget(keys);
//		long t2 = System.currentTimeMillis();
//		System.out.println((t2-t1)/1000.0);

		Thread.sleep(10000);

		// jedisCluster.close();

	}

	private static void init() {
		for (int i = 0; i < num; i++) {
			list.add("ljhtest" + i);
		}
		System.out.println(list.size());
	}

}
