package com.lujinhong.commons.others.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Hello world!
 *
 */
public class JedisBasicDemo {
	public static void main(String[] args) {
		JedisPool jedisPool = new JedisPool("localhost", 6379);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("k1", "v1");
			jedis.set("k2", "v2");
			System.out.println(jedis.get("k1"));
			System.out.println(jedis.get("k2"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close();

			jedisPool.close();
		}

	}
}
