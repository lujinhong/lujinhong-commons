/**
 * 
 */
package com.lujinhong.commons.others.jedis;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * date: 2016年3月21日 下午4:51:38
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年3月21日 下午4:51:38
 */

public class JedisFormalUtils {

	public static void main(String[] args) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("redis");

		JedisPoolConfig config = new JedisPoolConfig();
		String host = bundle.getString("redis.host");
		int port = Integer.parseInt(bundle.getString("redis.port"));
		int timeOut = Integer.parseInt(bundle.getString("redis.timeout"));
		String password = bundle.getString("redis.password");
		// config.setMaxActive(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
		config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
		JedisPool pool = new JedisPool(config, host, port, timeOut, password);

		Jedis jedis = pool.getResource();

		jedis.set("province", "gd");
		String province = jedis.get("province");
		System.out.println(province);
		jedis.del("province");

		jedis.close();
		pool.close();

	}

}
