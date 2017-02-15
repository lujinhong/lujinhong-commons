package com.lujinhong.commons.others.jedis;

import com.sun.tools.javac.util.BasicDiagnosticFormatter;
import redis.clients.jedis.*;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

/**
 * AUTHOR: LUJINHONG
 * CREATED ON: 17/1/6 09:32
 * PROJECT NAME: lujinhong-commons
 * DESCRIPTION:
 */
public class SdcDemo {

    private static JedisCluster jedisCluster = null;
    private static final String SEPERATOR = ":";

    //更好的办法是用单例返回对象，但对于demo类型的程序，这就没那么直观易懂了
    private static JedisCluster initJedisCluster() {
        JedisPoolConfig config = new JedisPoolConfig();
        //config.setMaxTotal(3);
        //config.setMaxIdle(2);

        Set<HostAndPort> hps = new HashSet<HostAndPort>();
        hps.add(new HostAndPort("10.170.11.126", 6379));
        hps.add(new HostAndPort("10.170.11.127", 6379));
        hps.add(new HostAndPort("10.170.10.126", 6379));
        hps.add(new HostAndPort("10.170.10.127", 6379));
        hps.add(new HostAndPort("10.170.12.126", 6379));
        hps.add(new HostAndPort("10.170.12.127", 6379));

        return new JedisCluster(hps, 5000, 10);
    }

    private static JedisSentinelPool pool = null;
    private static Jedis jedis = null;
    private  static Jedis initJedis(){
        Set<String> sentinels = new HashSet<>();
        sentinels.add("10.160.84.83:26379");
        sentinels.add("10.160.85.83:26379");
        sentinels.add("10.160.86.83:26379");
        pool = new JedisSentinelPool("edt_realtime", sentinels);
        return pool.getResource();
    }

    public static void main(String[] args) {

        double d = 105.6783;
        System.out.println(String.format("%.2f",d).toString());
//        if (null == jedisCluster) {
//            jedisCluster = initJedisCluster();
//        }
//
//        Long begin = System.currentTimeMillis();
//        for(int i =0; i< Integer.parseInt(args[0]); i++){
//            jedisCluster.set("lujinhongtest" + i, "value" + i);
//            jedisCluster.get("lujinhongtest" + i);
//        }
//        System.out.println("Time cost :" + (System.currentTimeMillis() - begin));



//        ResourceBundle bundle = ResourceBundle.getBundle("redis");
//        JedisPoolConfig config = new JedisPoolConfig();//可以设置
//
//
//        // config.setMaxActive(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
//        //config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
//        String host = bundle.getString("redis.host");
//        int port = Integer.parseInt(bundle.getString("redis.port"));
//        int timeOut = Integer.parseInt(bundle.getString("redis.timeout"));
//        //String password = bundle.getString("redis.password");

        //完整有5个参数，分别表示配置信息（有很多的配置选项）、主机、端口、超时设置、密码（这里没提供）简单的方式可以只提供hostname。
//        if(jedis == null){
//            jedis = initJedis();
//        }
//        Pipeline pipeline = jedis.pipelined();
//
//        Long begin = System.currentTimeMillis();
//        for(int i = 0; i< Integer.parseInt(args[0]); i ++){
//            pipeline.set("ljhtest" + i, "value" + i);
//        }
//        pipeline.sync();
//        pipeline.clear();
//        System.out.println("Time cost :" + (System.currentTimeMillis() - begin));
//
//        Long begin2 = System.currentTimeMillis();
//        List<String> keys = new ArrayList<>();
//        for(int i = 0; i< Integer.parseInt(args[0]); i ++){
//            keys.add("ljhtest" + i);
//        }
//        String[] keys2 = new String[keys.size()];
//        for(int i = 0; i< Integer.parseInt(args[0]); i ++){
//            keys2[i] = "ljhtest" + i;
//        }
//        Response<List<String>>  response = pipeline.mget(keys2);
//        try {
//            pipeline.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        List<String> values = response.get();
//        Map<String, String> keyValues = new HashMap<>();
//        for(int i = 0; i < Integer.parseInt(args[0]); i ++){
//            keyValues.put(keys2[i],values.get(i));
//        }
//        System.out.println(keyValues.get("ljhtest66"));
//        System.out.println("Time cost :" + (System.currentTimeMillis() - begin2));


//        String key = "lujinhong";
//        jedisCluster.del(key);
//        long preCount = 0;
//        long preTxid = 0;
//        for(int i = 1; i< 100 ; i++) {
//            String value = jedisCluster.get(key);
//
//            if ((null != value) && (value.length() != 0) && (value.contains(SEPERATOR))) {
//                System.out.println("1if");
//                preTxid = Long.parseLong(value.split(SEPERATOR)[0]);
//                preCount = Long.parseLong(value.split(SEPERATOR)[1]);
//                System.out.println(preCount);
//            }
//
//            System.out.println(preTxid+"llll"+i);
//            if (preTxid < i) {
//                System.out.println("2if");
//
//                preCount = preCount + 1;
//                System.out.println(preCount);
//                jedisCluster.set(key, i + SEPERATOR + preCount);
//            }
//        }
//        System.out.println(jedisCluster.get(key));

    }
}
