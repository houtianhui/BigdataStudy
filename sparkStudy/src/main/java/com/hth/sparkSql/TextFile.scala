//package com.hth.sparkSql
//
//import org.apache.spark.SparkContext
//import org.apache.spark.sql.SQLContext
//
///**
//  * Created by hth on 2017/3/10.
//  */
//object  TextFile{
//  def main(args:Array[String]){
//    //第一步
//    //构建SparkContext对象，主要要使用new调用构造方法，否则就变成使用样例类的Apply方法了
//    val sc = new SparkContext()
//    //构建SQLContext对象
//    val sqlContext = new SQLContext(sc)
//
//    //第二步
//    import sqlContext.implicits._
//    //第三步
//    case Person(name:String,age:Int)
//
//    //第四步，textFile从指定路径读取文件如果是集群模式要写hdfs文件地址；通过两个map操作将读取到的文件转换成Person类的对象，每一行对应一个Person对象；toDF将其转换成DataFrame
//    val people = sc.textFile("文件路径").map(_.split(",")).map{case (name,age) => Person(name,age.toInt)}.toDF()
//    //第五步
//    //DataFrame方法
//    println("------------------------DataFrame------------------------------------")
//    //赛选出age>10的记录，然后只选择name属性，show方法将其输出
//    people.where(people("age") > 10).select(people("name")).show()
//
//    //DSL
//    println("---------------------------DSL---------------------------------")
//    people.where('age > 10).select('name).show()
//
//    //SQL
//    println("-----------------------------SQL-------------------------------")
//    //将people注册成people表
//    people.registerTempTable("people")
//    //使用sqlContext的sql方法来写SQL语句
//    //查询返回的是RDD，所以对其进行collect操作，之后循环打印
//    sqlContext.sql("select name from people where age > 10").collect.foreach(println)
//
//    //保存为parquet文件，之后的parquet演示会用到
//    people.saveAsParquet("保存的路径")
//    val sc = new SparkContext()
//    val sql = new SQLContext(sc)
//    import sql.implicits._
//    val json = sql.jsonFile(args(0))
//    println("------------------------DataFrame------------------------------------")
//    println(json.where(json("age") > 10).select(json("name")).show())
//
//    println("---------------------------DSL---------------------------------")
//    println(json.where('age > 10).select('name).show())
//
//    println("-----------------------------SQL-------------------------------")
//    json.registerTempTable("json")
//    sql.sql("select name from json where age > 10").map(p => "name:" + p(0)).collect().foreach(println)
//
//    val sc = new SparkContext()
//    val sql = new SQLContext(sc)
//    import sql.implicits._
//    val parquet = sql.parquetFile(args(0))
//    println("------------------------DataFrame------------------------------------")
//    println(parquet.where(parquet("age") > 10).select(parquet("name")).show())
//
//    println("---------------------------DSL---------------------------------")
//    println(parquet.where('age > 10).select('name).show())
//
//    println("-----------------------------SQL-------------------------------")
//    parquet.registerTempTable("parquet")
//    sql.sql("select name from parquet where age > 10").map(p => "name:" + p(0)).collect().foreach(println)
//  }
//}
