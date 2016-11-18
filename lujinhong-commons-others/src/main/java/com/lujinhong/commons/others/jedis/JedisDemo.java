/**
 *
 */
package com.lujinhong.commons.others.jedis;

import java.io.IOException;
import java.util.*;

import org.eclipse.jdt.internal.core.SourceType;
import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

/**
 * date: 2016年3月21日 下午4:51:38
 *
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年3月21日 下午4:51:38
 */

public class JedisDemo {
    private static final Logger LOG = LoggerFactory.getLogger(JedisDemo.class);

    public static void main(String[] args) {
        Log.info("begin");

        //sentinalDemo();
        ClusterDemo();
        mgetInDifferentSoltDemo();
        ClusterMgetDemo();

        Log.info("finish");
    }


    private static void sentinalDemo() {
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        JedisPoolConfig config = new JedisPoolConfig();//可以设置
        // config.setMaxActive(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
        //config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
        String host = bundle.getString("redis.host");
        int port = Integer.parseInt(bundle.getString("redis.port"));
        int timeOut = Integer.parseInt(bundle.getString("redis.timeout"));
        //String password = bundle.getString("redis.password");

        //完整有5个参数，分别表示配置信息（有很多的配置选项）、主机、端口、超时设置、密码（这里没提供）简单的方式可以只提供hostname。
        try (//1、创建JedisPool对象
             JedisPool pool = new JedisPool(config, host, port, timeOut);
             //2、从pool中获取Jedis对象
             Jedis jedis = pool.getResource();) {

            //3、操作redis
            jedis.set("province", "gd");
            String province = jedis.get("province");
            LOG.info(province);
            jedis.del("province");
        }
    }

    private static void sentinalPipelineDemo() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        JedisPoolConfig config = new JedisPoolConfig();//可以设置
        // config.setMaxActive(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
        //config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
        String host = bundle.getString("redis.host");
        int port = Integer.parseInt(bundle.getString("redis.port"));
        int timeOut = Integer.parseInt(bundle.getString("redis.timeout"));
        //String password = bundle.getString("redis.password");

        //完整有5个参数，分别表示配置信息（有很多的配置选项）、主机、端口、超时设置、密码（这里没提供）简单的方式可以只提供hostname。
        Pipeline pipeline = null;
        try (//1、创建JedisPool对象
             JedisPool pool = new JedisPool(config, host, port, timeOut);
             //2、从pool中获取Jedis对象及pipeline对象
             Jedis jedis = pool.getResource();) {
            pipeline = jedis.pipelined();
            //3、操作redis
            pipeline.set("province", "gd");
            pipeline.get("province");
            pipeline.set("hh", "kk");
            pipeline.set("kk", "kk");
            pipeline.sync();

            //4、sentianl的mget操作，以及若需要返回结果时如何获取。
            String[] keys = new String[]{"key1","key2","key3"};
            Response<List<String>>  response = pipeline.mget(keys);
            pipeline.sync();
            pipeline.close();
            List<String> thisSlotValues = response.get();

        } finally {
            pipeline.close();
        }
    }


    //cluster不支持pipeline。
    private static void ClusterPipelineDemo() {
        // 超时，最大的转发数，最大链接数，最小链接数都会影响到集群199,237,204
        try (//1、创建JedisCluster对象
             JedisCluster jedisCluster = initJedisCluster()) {

            //2、操作redis
            jedisCluster.set("lujinhong", "value2");
            LOG.info("value of lujinhong is: {}", jedisCluster.get("lujinhong"));
            System.out.println(jedisCluster.get("lujinhong"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ClusterDemo() {
        // 超时，最大的转发数，最大链接数，最小链接数都会影响到集群199,237,204
        try (//1、创建JedisCluster对象
             JedisCluster jedisCluster = initJedisCluster()) {

            //2、操作redis
            jedisCluster.set("lujinhong", "value2");
            LOG.info("value of lujinhong is: {}", jedisCluster.get("lujinhong"));
            System.out.println(jedisCluster.get("lujinhong"));

            //测试如果没有这个值的情况下，会返回什么结果
            String noThisValue = jedisCluster.get("no_this_key");//结果返回的是null。
            if (null == noThisValue) {
                System.out.println("no_this_value is null.");
            } else if ("" == noThisValue) {
                System.out.println("no_this_value is empty string.");
            } else {
                System.out.println("no_this_value is " + noThisValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ClusterMgetDemo() {

        // 超时，最大的转发数，最大链接数，最小链接数都会影响到集群
        try (//1、创建JedisCluster对象
             JedisCluster jedisCluster = initJedisCluster()) {

            //2、操作redis
            jedisCluster.set("lujinhong", "value2");
            LOG.info("value of lujinhong is: {}", jedisCluster.get("lujinhong"));

            //测试如果没有这个值的情况下，会返回什么结果
            List<String> values = jedisCluster.mget("no_this_key", "lujinhong");
            System.out.println("Result value sizes is " + values.size());
            String noThisValue = values.get(0);
            if (null == noThisValue) {
                System.out.println("no_this_value is null.");
            } else if ("" == noThisValue) {
                System.out.println("no_this_value is empty string.");
            } else {
                System.out.println("no_this_value is " + noThisValue);
            }
            System.out.println("Value for key lujinhong is " + values.get(1));

            //Exception happens: "No way to dispatch this command to Redis Cluster because keys have different slots"
            //mget要求请求的key都落在cluster集群的同一个slot
            List<String> mgetExceptionValues = jedisCluster.mget("lujinhong", "lujinhongtest");
            for (String value : mgetExceptionValues) {
                System.out.println(value);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void mgetInDifferentSoltDemo() {

        // 超时，最大的转发数，最大链接数，最小链接数都会影响到集群
        try (//1、创建JedisCluster对象
             JedisCluster jedisCluster = initJedisCluster()) {

            //2、操作redis
            jedisCluster.set("lujinhong", "value2");
            LOG.info("value of lujinhong is: {}", jedisCluster.get("lujinhong"));
            List<String> keys = new LinkedList<>();
            keys.add("lujinhong");
            keys.add("lujinhongtest");
            keys.add("ljhtest1");
            keys.add("ljhtest2");
            keys.add("ljhtest3");

            List<String> values = mgetInDifferentSolt(keys);
            for (String value : values) {
                System.out.println(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //修改源码，如果mget抛出异常，则调用此方法。
    //TODO：支持多种数据类型
    private static List<String> mgetInDifferentSolt(List<String> keys) {
        List<String> sortResult = new LinkedList<>();

        Map<Integer, Set<String>> slotKeysMap = new HashMap<>();
        //将处于不同slot的key分别放在不同的HashSet中。
        for (String key : keys) {
            int slot = JedisClusterCRC16.getSlot(key);
            Set<String> slotKeys = slotKeysMap.get(slot);
            if (null == slotKeysMap.get(slot)) {
                slotKeys = new HashSet<>();
            }
            slotKeys.add(key);
            slotKeysMap.put(slot, slotKeys);
        }

        //发起多个请求，分别获取不同slot的结果，然后组合返回。
        JedisCluster jedisCluster = initJedisCluster();
        Map<String, String> result = new HashMap<>();
        //如果性能真的不能满足需求，则多个线程同时去mget，每个线程负责一个。但这需要小心同步的问题。加锁会导致性能下降，未必比串行的快。
        for (Map.Entry<Integer, Set<String>> slotKeys : slotKeysMap.entrySet()) {
            String[] oneSlotKeys = new String[slotKeys.getValue().size()];
            int i = 0;
            for (String key : slotKeys.getValue()) {
                oneSlotKeys[i] = key;
                ++i;
            }
            ArrayList<String> oneSlotValues = (ArrayList<String>) jedisCluster.mget(oneSlotKeys);
            int j = 0;
            for (String value : oneSlotValues) {
                result.put(oneSlotKeys[j], value);
                ++j;
            }
        }

        //将结果按顺序返回
        for (String key : keys) {
            sortResult.add(result.get(key));
        }

        return sortResult;
    }


    //更好的办法是用单例返回对象，但对于demo类型的程序，这就没那么直观易懂了
    private static JedisCluster initJedisCluster() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(3);
        config.setMaxIdle(2);

        Set<HostAndPort> hps = new HashSet<HostAndPort>();
        hps.add(new HostAndPort("10.170.10.83", 6379));
        hps.add(new HostAndPort("10.170.10.84", 6379));
        hps.add(new HostAndPort("10.170.10.85", 6379));
        hps.add(new HostAndPort("10.170.10.86", 6379));
        hps.add(new HostAndPort("10.170.10.87", 6379));
        hps.add(new HostAndPort("10.170.10.88", 6379));
        hps.add(new HostAndPort("10.170.11.83", 6379));
        hps.add(new HostAndPort("10.170.11.84", 6379));

        return new JedisCluster(hps, 5000, 10, config);
    }


}
