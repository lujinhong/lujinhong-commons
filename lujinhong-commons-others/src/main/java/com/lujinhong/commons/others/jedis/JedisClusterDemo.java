package com.lujinhong.commons.others.jedis;

import java.io.IOException;
import java.util.HashSet;  
import java.util.Set;  
  
import redis.clients.jedis.HostAndPort;  
import redis.clients.jedis.JedisCluster;  
import redis.clients.jedis.JedisPoolConfig;  

/**
* date: 2016年7月19日 上午11:07:09
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年7月19日 上午11:07:09
*/

public class JedisClusterDemo {
	
    public static void main(String[] args) throws IOException { 
    	int num = 10000;
    	if(args.length > 0 ){
    		num = Integer.parseInt(args[0]);
    	}
    	  
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
        
        //test set
        long start = System.currentTimeMillis();  
        for (int i = 0; i < num; i++) {  
            jedisCluster.set("sn" + i, "n" + i);  
        }  
        long end = System.currentTimeMillis();  
        System.out.println("It takes " + (end - start) / 1000.0 + " seconds to put " + num + " data.");  
        
        //test get
        start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {  
            jedisCluster.get("sn" + i);  
        }  
        end = System.currentTimeMillis();  
        System.out.println("It takes " + (end - start) / 1000.0 + " seconds to get " + num + " data.");  
  
        jedisCluster.close();  
  
    }  

}
