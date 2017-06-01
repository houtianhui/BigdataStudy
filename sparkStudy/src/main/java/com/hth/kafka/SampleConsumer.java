package com.hth.kafka;

/**
 * Created by hth on 2017/3/10.
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
//import cn.com.dimensoft.kafka.constant.Constant;

/**
 * SampleConsumer是自定义的consumer，用来从broker pull消息对处理：
 * class： SampleConsumer
 * package： cn.com.dimensoft.kafka
 * author：zxh
 * time： 2015年9月25日 下午4:41:26
 * description：
 *  step1 : 创建存放配置信息的properties
 *  step2 : 将properties封装到ConsumerConfig中
 *  step3 : 调用Consumer的静态方法创建ConsumerConnector
 *  step4 : 根据创建好的ConsumerConnector对象创建MessageStreams集合
 *  step5 : 根据具体的topic名称得到数据流KafkaStream
 *  step6 : 调用KafkaStream的iterator拿到ConsumerIterator对应，然后就可以迭代获得producer发送过来的消息了
 */
public class SampleConsumer {

    public static void main(String[] args) throws InterruptedException {

        // step1 : 创建存放配置信息的properties
        Properties props = new Properties();

        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "1");
        // 下面这2个参数需要设置，否则consumer每次启动都会从头开始读取数据
        props.put("auto.commit.enable", "true");
        props.put("auto.commit.interval.ms", "1000");

        // What to do when there is no initial offset in ZooKeeper or if an
        // offset is out of range
        props.put("auto.offset.reset", "smallest");

        // step2 : 将properties封装到ConsumerConfig中
        ConsumerConfig config = new ConsumerConfig(props);

        // step3 : 调用Consumer的静态方法创建ConsumerConnector
        ConsumerConnector connector = Consumer
                .createJavaConsumerConnector(config);

        // step4 : 根据创建好的ConsumerConnector对象创建MessageStreams集合
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        // 将每个topic对应的线程数添加到map中，topicCountMap中的topic对应的value值一直没测出实际效果
        topicCountMap.put(Constant.TOPIC, 1);
        // 根据填充好的map获得streams集合
        // a map of (topic, list of KafkaStream) pairs
        Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector
                .createMessageStreams(topicCountMap);

        // step5 : 根据具体的topic名称得到数据流KafkaStream
        KafkaStream<byte[], byte[]> stream = streams.get(Constant.TOPIC).get(0);

        // step6 : 调用KafkaStream的iterator拿到ConsumerIterator
        // 然后就可以迭代获得producer发送过来的消息了
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();

        MessageAndMetadata<byte[], byte[]> mm = null;
        while (iterator.hasNext()) {
            mm = iterator.next();
            System.out.println(//
                    " group " + props.get("group.id") + //
                            ", partition " + mm.partition() + ", " + //
                            new String(mm.message()));
            Thread.sleep(100);
        }
    }
}