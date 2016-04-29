package com.lujinhong.commons.spark.hbase

import org.apache.spark._
import org.apache.spark.rdd.NewHadoopRDD
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.client.HTable
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

object SparkConnectHbase2 extends Serializable {

  def main(args: Array[String]) {
    new SparkConnectHbase2().toHbase();
  }

}

class SparkConnectHbase2 extends Serializable {

  def toHbase() {
    val conf = new SparkConf().setAppName("ljh_ml3");
    val sc = new SparkContext(conf)

    val tmp = sc.parallelize(Array("l", "j", "h", "u")).map({ a => 
      val configuration = HBaseConfiguration.create();
      configuration.set("hbase.zookeeper.property.clientPort", "2181");
      configuration.set("hbase.zookeeper.quorum", "114.113.203.66");
      configuration.set("hbase.master", "114.113.203.66:60000");
      val table = new HTable(configuration, "ljh_test3");
      var put = new Put(Bytes.toBytes(a+""));
      put.add(Bytes.toBytes("f"), Bytes.toBytes("c"), Bytes.toBytes(a + "value"));
      table.put(put);
      table.flushCommits();
    }).saveAsTextFile("/tmp/lujinhong/test23")

  }

}