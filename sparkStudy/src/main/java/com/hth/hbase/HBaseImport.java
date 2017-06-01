package com.hth.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * HBaseConfiguration, 通过此类对HBase进行配置
 HBaseAdmin, 提供一个接口来管理HBase数据库的表信息, 提供创建, 删除表, 列出表项, 使表有效或无效, 以及添加或删除列族成员
 HTableDescriptor, 包含了表的名字及对应表的列族
 HColumnDescriptor, 维护关于列族的信息
 HTable, 用来与HBase表进行通信
 Put, 用来对单个行执行添加操作
 Get, 用来获取单个行的相关信息
 Result, 存储Get或者Scan操作后获取的表的单行值
 5.1. HBase体系结构
 HBase的服务器体系遵从简单的主从服务器架构, 由HRegion服务器群和HBase服务器构成, Master服务器负责管理所有的HRegion服务器, 而HBase中所有的服务器通过ZooKeeper来进行协调并处理HBase服务器运行期间可能遇到的错误.

 HBase逻辑上的表可能会被划分为多个HRegion, 然后存储在HRegion服务器上.

 HBase不涉及数据的直接删除和更新, 当Store中的Storefile数量超出阈值会触发合并操作
 HMaster的主要任务是告诉每个HRegion服务器它要维护那些HRegion
 ZooKeeper存储的是HBase中ROOT表和META表的位置, ZooKeeper还负责监控各个机器的状态
 元数据子表采用三级索引结构: 根子表->用户表的元数据表->用户表
 */

public class HBaseImport {
	static class BatchMapper extends Mapper<LongWritable, Text, LongWritable, Text> {
	@Override
	protected void map(LongWritable key, Text value,
                       Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] splited = line.split("\t");
		SimpleDateFormat simpleDateFormatimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = simpleDateFormatimpleDateFormat.format(new Date(Long.parseLong(splited[0].trim())));
		String rowKey=splited[1]+"_"+format;
		Text v2s = new Text();
		v2s.set(rowKey+"\t"+line);
		context.write(key, v2s);
		}
	}
	static class BatchReducer extends TableReducer<LongWritable, Text, NullWritable> {
		private String family="cf";

		@Override
		protected void reduce(LongWritable arg0, Iterable<Text> v2s,
                              Reducer<LongWritable, Text, NullWritable, Mutation>.Context context)
				throws IOException, InterruptedException {
			for (Text v2 : v2s) {
				String[] splited = v2.toString().split("\t");
				String rowKey = splited[0];
				Put put = new Put(rowKey.getBytes());
				put.add(family.getBytes(), "raw".getBytes(), v2.toString().getBytes());
				put.add(family.getBytes(), "rePortTime".getBytes(), splited[1].getBytes());
				put.add(family.getBytes(), "msisdn".getBytes(), splited[2].getBytes());
				put.add(family.getBytes(), "apmac".getBytes(), splited[3].getBytes());
				put.add(family.getBytes(), "acmac".getBytes(), splited[4].getBytes());
				put.add(family.getBytes(), "host".getBytes(), splited[5].getBytes());
				put.add(family.getBytes(), "siteType".getBytes(), splited[6].getBytes());
				put.add(family.getBytes(), "upPackNum".getBytes(), splited[7].getBytes());
				put.add(family.getBytes(), "downPackNum".getBytes(), splited[8].getBytes());
				put.add(family.getBytes(), "upPayLoad".getBytes(), splited[9].getBytes());
				put.add(family.getBytes(), "downPayLoad".getBytes(), splited[10].getBytes());
				put.add(family.getBytes(), "httpStatus".getBytes(), splited[11].getBytes());
				context.write(NullWritable.get(), put);
			}
		}
	}
	private static final String TableName = "waln_log";
	public static void main(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.rootdir", "hdfs://192.168.1.177:9000/hbase");
		conf.set("hbase.zookeeper.quorum", "192.168.1.177:2181");
		conf.set(TableOutputFormat.OUTPUT_TABLE, TableName);
		
		Job job = new Job(conf, HBaseImport.class.getSimpleName());
		TableMapReduceUtil.addDependencyJars(job);
		job.setJarByClass(HBaseImport.class);
		
		job.setMapperClass(BatchMapper.class);
		job.setReducerClass(BatchReducer.class);
		
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TableOutputFormat.class);
		
		FileInputFormat.setInputPaths(job, "hdfs://192.168.1.177:9000/data");
		job.waitForCompletion(true);
	}
}
