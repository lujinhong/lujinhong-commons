package com.lujinhong.commons.spark.grepword

import org.apache.spark.SparkContext

/**
  * AUTHOR: LUJINHONG
  * CREATED ON: 17/3/2 14:05
  * PROJECT NAME: Default (Template) Project
  * DESCRIPTION: 
  */
object GrepWord {
  def grepCountLog(sc:SparkContext,path: String, keyWord: String) {

    println("grep " + keyWord + " in " + path + ", the lineCount is: ")
    val all = sc.textFile(path)
    val ret = all.filter(line => line.contains(keyWord))
    println(ret.count)
  }

  def main(args: Array[String]) {
    val sc = new SparkContext();
    grepCountLog(sc,"/src/20151201", "\"server\": \"20002\"");
  }
}
