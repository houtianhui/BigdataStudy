//package com.hth.sparkstreaming;
//
///**
// * Created by hth on 2017/3/10.
// */
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;
//import org.apache.spark.streaming.Durations;
//import org.apache.spark.streaming.api.java.JavaDStream;
//import org.apache.spark.streaming.api.java.JavaPairDStream;
//import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import scala.Tuple2;
//
//import java.util.Arrays;
//import java.util.Iterator;
//
///**
// * @author qifuguang
// * @date 15/12/8 14:55
// *
// * 本文分步骤讲解如何创建一个简单的spark-streaming程序，例子是一个简单的WordCount程序，从socket接收输入的句子，用空格分隔出所有单词，然后统计各个单词出现的次数，最后打印出来。
// * 需要说明的是，本文不会详细讲解代码，仅仅是带领大家先体验一把spark-streaming的流式计算功能。
// * 话不多说，开始动手…
// * 启动netcat服务
//
//上一个步骤已经将spark程序代码写完了，现在需要的是模拟一个数据输入源，本例使用netcat工具模拟socket，向本机的9999端口输入数据，供spark程序消费。
//启动netcat服务，使用的端口是9999：
// */
//public class SparkStreamingDemo {
//    public static void main(String[] args) {
//        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount");
//        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(10));
//        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);
//
//        // Split each line into words
//        JavaDStream<String> words = lines.flatMap(
//                new FlatMapFunction<String, String>() {
//                    public Iterator<String> call(String x) {
//                        return (Iterator<String>) Arrays.asList(x.split(" "));
//                    }
//                });
//
//        // Count each word in each batch
//        JavaPairDStream<String, Integer> pairs = words.mapToPair(
//                new PairFunction<String, String, Integer>() {
//                    public Tuple2<String, Integer> call(String s) {
//                        return new Tuple2<String, Integer>(s, 1);
//                    }
//                });
//        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey(
//                new Function2<Integer, Integer, Integer>() {
//                    public Integer call(Integer i1, Integer i2) {
//                        return i1 + i2;
//                    }
//                });
//
//        // Print the first ten elements of each RDD generated in this DStream to the console
//        wordCounts.print();
//
//        jssc.start();              // Start the computation
//        jssc.awaitTermination();   // Wait for the computation to terminate
//    }
//}
