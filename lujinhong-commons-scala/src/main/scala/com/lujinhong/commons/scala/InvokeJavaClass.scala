package com.lujinhong.commons.scala

object InvokeJavaClass {
  
  def main(args :Array[String])={
    val javaClass2 = new MyJavaClass()
    val addResult = javaClass2.adder(3,4)
    println(addResult);
  }
  
}