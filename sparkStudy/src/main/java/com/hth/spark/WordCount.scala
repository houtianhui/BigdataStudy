package com.hth.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by hth on 2017/3/9.
  */
object WordCount {
  def main(args: Array[String]) {
    if (args.length < 0) {
      System.err.println("Usage:<File>")
      System.exit(1)
    }
    val conf = new SparkConf().setAppName("WordCount").setMaster("local")
    val sc = new SparkContext(conf)

    val line = sc.textFile("/Users/hth/Downloads/words.txt")

    val words = line.flatMap(_.split("")).map((_, 1))
    print(words)
    val reducewords = words.reduceByKey(_ + _).collect().foreach(println)
    print(reducewords)

    sc.stop()
  }
}
