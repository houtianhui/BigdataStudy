package com.hth.kafka;

/**
 * Created by hth on 2017/3/10.
 */
import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * SamplePartition是自定义的partition，用来对消息进行分区：
 * class： SamplePartition
 * package： cn.com.dimensoft.kafka
 * author：zxh
 * time： 2015年9月28日 下午5:37:19
 * description： 设置自定义的partition，指明当topic有多个partition时如何对message进行分区
 */
public class SamplePartition implements Partitioner {

    /**
     * constructor
     * author：zxh
     * @param verifiableProperties
     * description： 去除该构造方法后启动producer报错NoSuchMethodException
     */
    public SamplePartition(VerifiableProperties verifiableProperties) {

    }

    /**
     * 这里对message分区的依据只是简单的让key(这里的key就是Producer[K,V]中的K)对partition的数量取模
     */
    public int partition(Object obj, int partitions) {

        // 对partitions数量取模
        return Integer.parseInt(obj.toString()) % partitions;
    }

}
