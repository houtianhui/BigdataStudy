package com.hth.kafka;

/**
 * Created by hth on 2017/3/10.
 */
import java.util.Properties;

//import cn.com.dimensoft.kafka.constant.Constant;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * SampleProducer是自定义的producer，用来生产消息并将消息push给broker：
 * class： SampleProducer
 * package： cn.com.dimensoft.kafka
 * author：zxh
 * time： 2015年9月25日 下午4:05:51
 * description：
 *  step1 : 创建存放配置信息的properties
 *  step2 : 将properties封装到ProducerConfig中
 *  step3 : 创建producer对象
 *  step4 : 发送数据流
 */
public class SampleProducer {

    public static void main(String[] args) throws InterruptedException {

        // step1 : 创建存放配置信息的properties
        Properties props = new Properties();

        // 指定broker集群
        props.put("metadata.broker.list", "localhost:9092");
        /**
         * ack机制
         * 0 which means that the producer never waits for an acknowledgement from the broker
         * 1 which means that the producer gets an acknowledgement after the leader replica has received the data
         * -1 The producer gets an acknowledgement after all in-sync replicas have received the data
         */
        props.put("request.required.acks", "1");
        // 消息发送类型 同步/异步
        props.put("producer.type", "sync");
        // 指定message序列化类，默认kafka.serializer.DefaultEncoder
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        // 设置自定义的partition，当topic有多个partition时如何对message进行分区
        props.put("partitioner.class", "cn.com.dimensoft.kafka.SamplePartition");

        // step2 : 将properties封装到ProducerConfig中
        ProducerConfig config = new ProducerConfig(props);

        // step3 : 创建producer对象
        Producer<String, String> producer = new Producer<String, String>(config);

        for (int i = 1; i <= 50; i++) {
            // step4 : 发送数据流
            producer.send(new KeyedMessage<String, String>(Constant.TOPIC, //
                    i + "", //
                    String.valueOf("我是 " + i + " 号")));
            Thread.sleep(10);
        }
    }

}