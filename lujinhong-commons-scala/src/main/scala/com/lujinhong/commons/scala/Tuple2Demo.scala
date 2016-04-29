package com.lujinhong.commons.scala

object Tuple2Demo{
  		def main(args : Array[String])  {
  		  var res = new Tuple2Demo().test("hello")
  		  print(res._1 + " : " + res._2);
  		}
}



class Tuple2Demo{

      def test(v : String) : Tuple2[String, Int] = {
		  return Tuple2(v,v.length())
		}
}