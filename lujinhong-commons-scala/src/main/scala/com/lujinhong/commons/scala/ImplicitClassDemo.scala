package com.lujinhong.commons.scala

class ImplicitClassDemo {}

object ImplicitClassDemo {

  def main(args: Array[String]) {
    println("d".addOne(1))
    println("kk".addOne(3))
  }

  implicit class Caculator(x: String) {
    def addOne(a: Int): Int = a + 1
  }

}

