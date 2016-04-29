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

object SparkConnectHbase {

  def main(args: Array[String]) {



    val configuration = HBaseConfiguration.create();
    configuration.set("hbase.zookeeper.property.clientPort", "2181");
    configuration.set("hbase.zookeeper.quorum", "114.113.203.66");
    configuration.set("hbase.master", "114.113.203.66:60000");
    val hadmin = new HBaseAdmin(configuration);

    val table = new HTable(configuration, "ljh_test3");
    for (i <- 101 to 151) {
      var put = new Put(Bytes.toBytes("row" + i))
      put.add(Bytes.toBytes("f"), Bytes.toBytes("c"), Bytes.toBytes("value " + i))
      table.put(put)
    }
    table.flushCommits()
    
  //  val tmp = sc.parallelize(Array(601, 701, 801, 901)).map( a => a+"value" )
//    for(a <- tmp){
////      configuration.set("hbase.zookeeper.property.clientPort", "2181");
////      configuration.set("hbase.zookeeper.quorum", "114.113.203.66");
////      configuration.set("hbase.master", "114.113.203.66:60000");
////      val table = new HTable(configuration, "ljh_test3");
//      var put = new Put(Bytes.toBytes(a));
//      put.add(Bytes.toBytes("f"), Bytes.toBytes("c"), Bytes.toBytes(a + "value"));
//      table.put(put);
//    }
//          table.flushCommits();


//    //将一个RDD写入hbase
//    sc.parallelize(Array(601, 701, 801, 901)).foreach({ a =>
//      val configuration = HBaseConfiguration.create();
//      configuration.set("hbase.zookeeper.property.clientPort", "2181");
//      configuration.set("hbase.zookeeper.quorum", "114.113.203.66");
//      configuration.set("hbase.master", "114.113.203.66:60000");
//      val table = new HTable(configuration, "ljh_test3");
//      var put = new Put(Bytes.toBytes(a));
//      put.add(Bytes.toBytes("f"), Bytes.toBytes("c"), Bytes.toBytes(a + "value"));
//      table.put(put);
//      table.flushCommits();
//    })

  }

}