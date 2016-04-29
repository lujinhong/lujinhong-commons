package com.lujinhong.commons.scala

import scala.io.Source
import java.io.PrintWriter

object IODemo {

  def main(args: Array[String]) = {
    val fileName = "/Users/liaoliuqing/setupfile/eclipse/notice.html"
    val outFile = "/Users/liaoliuqing/Downloads/1.txt"

    readAndPrint(fileName)

    //从返回的数组中读取第10行
    val lines = readToArray(fileName)
    println(lines(10))

    //将第15行数据输出到一个文件中
    writeToFile(outFile, lines(15))
    

  }

  //打印出文件中的所有内容
  def readAndPrint(fileName: String) {
    val source = Source.fromFile(fileName)
    val lineIterator = source.getLines()
    for (l <- lineIterator) {
      println(l)
    }
  }

  //将文件按行读入一个数组并返回
  def readToArray(fileName: String) = {
    val source = Source.fromFile(fileName)
    val lines = source.getLines().toArray
    lines
  }

  //将内容写入某个文件中,由于scala没有提供写文件的支持，可以使用java.io中的类代替
  def writeToFile(outFile: String, content: String) {
    val out = new PrintWriter(outFile)
    out.write(content)
    out.close()
  }
}
