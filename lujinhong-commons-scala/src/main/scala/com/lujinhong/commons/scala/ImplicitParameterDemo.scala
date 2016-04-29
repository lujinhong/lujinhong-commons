package com.lujinhong.commons.scala



object ImplicitParameterDemo {

  def testParam(implicit name: String) {
    println(name)
  }

  implicit val name = "lujinhong"

  def main(args: Array[String]) {
    testParam("My name")
    testParam
  }
}