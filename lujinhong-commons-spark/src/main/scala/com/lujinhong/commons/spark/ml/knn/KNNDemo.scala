package com.lujinhong.commons.spark.ml.knn

import org.apache.spark.SparkContext

import scala.collection.mutable.{SortedSet,HashMap}

/**
  * AUTHOR: LUJINHONG
  * CREATED ON: 17/3/2 14:06
  * PROJECT NAME: lujinhong-commons
  * DESCRIPTION: KNN算法的示例。
  * 示例数据如下：
  * $ cat knn_training_data.txt
    ID1 172 60 1
    ID2 163 50 0
    ID3 188 70 1
    ID4 155 40 0
    $ cat knn_to_do_data.txt
    ID5 164 54
    ID6 199 82
    ID7 172 50
  * 输出如下：
  * $ hadoop fs -cat /tmp/ljhn1829/ml/knn/result/\*
    ID5 0
    ID6 1
    ID7 0
  *
  */
object KNNDemo {
  val TRAINING_DATA_PATH = "/tmp/ljhn1829/ml/knn/training_data";
  val TO_DO_DATA_PATH = "/tmp/ljhn1829/ml/knn/to_classify_data"
  val OUTPUT_PATH = "/tmp/ljhn1829/ml/knn/result"
  val SEPARATOR = " "
  val K = 3
  val MALE_LABEL = "1"
  val FEMALE_LABLE = "0"

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext()
    var traingDataSetBroadcast = sc.broadcast(sc.textFile(TRAINING_DATA_PATH).collect().toSet);
    sc.textFile(TO_DO_DATA_PATH).map(line => classify(line, traingDataSetBroadcast.value)).saveAsTextFile(OUTPUT_PATH)
  }


  def classify(line: String, traingDataSet: Set[String]): String = {
    //记录与待分类元组最小的3个距离
    var minKDistanceSet = SortedSet[Double]()
    //记录与待分类元组最小的3个距离及其对应的分类。
    var minKDistanceMap = HashMap[Double, Int]()
    for (i <- 1 to K) {
      minKDistanceSet += Double.MaxValue
    }

    val info = line.trim.split(SEPARATOR)
    val id = info(0)
    val height = info(1).toDouble
    val weight = info(2).toDouble
    for (trainSampleItem <- traingDataSet) {
      val sampleInfo = trainSampleItem.trim().split(SEPARATOR)
      val distance = Math.sqrt(Math.pow((height - sampleInfo(1).toDouble), 2) + Math.pow((weight - sampleInfo(2).toDouble), 2))
      if (distance < minKDistanceSet.lastKey) {
        minKDistanceSet -= minKDistanceSet.lastKey
        minKDistanceSet += distance
        minKDistanceMap += ((distance, sampleInfo(3).toInt))
        if (minKDistanceMap.size >= 3) {
          minKDistanceMap -= minKDistanceSet.lastKey
        }
      }

    }
    //根据距离最近的3个样本分类，得出最终分类结果。
    var count = 0
    for (entry <- minKDistanceMap) {
      count += entry._2
    }
    var result = FEMALE_LABLE
    if (count > K / 2) {
      result = MALE_LABEL
    }
    return id + SEPARATOR + result
  }


}
