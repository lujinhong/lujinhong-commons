package com.lujinhong.commons.spark.grepword

import org.apache.spark.{SparkConf, SparkContext}
object GrepWord2 {

  //判断这行日志是否包括任何一个关键字，只要包含一个，就为true
  def containLog(line: String, keywords: Array[String]): Boolean = {
    for (keyword <- keywords) {
      if (line.contains(keyword)) return true;
    }
    return false;
  }

  //使用累加器
  //TODO：分别记录每个关键字的数量
  def grepCountLog(sc: SparkContext, path: String, keywords: Array[String]) {
    val all = sc.textFile(path)
    val logcount = sc.accumulator(0)
    val allLogCount = all.map(line => {
      if(containLog(line, keywords)) {
        logcount += 1
      }
    }).count
    
    println("There are " + allLogCount + "in " + path)
    println(logcount.value + " lines fit the keywords.")

  }
  
  /*
   * args(0):需要grep的文件目录，默认为hdfs上的路径
   * args(1)：grep的关键字，默认为hdfs上的路径
   */
  def main(args: Array[String]) {
    val sc = new SparkContext(new SparkConf().setAppName("ljh_log_statistics"));
    val keywords = sc.textFile(args(1)).collect()
    println("These keywords will be count:") 
    for(keyword <- keywords){
      print(keyword + "\t")
    }
    println()
    grepCountLog(sc, args(0),keywords)
  }
}

/*
/home/hadoop/spark/bin/spark-submit --master yarn-client --name ljh_grep_count_word --class com.lujinhong.sparkdemo.GrepWord2 sparkdemo-0.0.1-SNAPSHOT.jar /src/gamein/g18_us/20160125 /tmp/keywords.txt
 */

