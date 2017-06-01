package com.hth.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by hth on 2017/3/8.
  */
object App {
  def main(args : Array[String]) {
    println("Hello World!")
    val sparkConf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(sparkConf)
    val a = sc.parallelize(List(1, 2, 3, 4))
    a.persist();
    println(a.count())
    println("============================")
    a.collect().foreach(println)
  }

}
