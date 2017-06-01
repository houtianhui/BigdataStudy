package com.hth.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.hth.hadoop.TrafficSumApp.deleteOutDir;

//作业：HDFS中有一个目录，里面有2个文件，要求对目录中的所有文件进行单词计数。如果目录中有子目录，子目录中可能有很多文件哪？
public class WordCountApp{

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
		Configuration conf = new Configuration();
        conf.set("mapred.job.tracker", "localhost:9001");
        //本地提交模式
        conf.set("mapreduce.framework.name","local");
        conf.set("fs.defaultFS","hdfs://localhost:9000");
		Job job = Job.getInstance(conf,WordCountApp.class.getSimpleName());
		job.setJarByClass(WordCountApp.class);
        FileInputFormat.setInputPaths(job, new Path("hdfs://localhost:9000/hello"));
        job.setInputFormatClass(TextInputFormat.class);
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);


        job.setReducerClass(MyReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        String OUT_DIR = "hdfs://localhost:9000/out1";
        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job,new Path(OUT_DIR));
        job.setOutputFormatClass(TextOutputFormat.class);

        deleteOutDir(conf, OUT_DIR);
        job.waitForCompletion(true);




	}

	public static void deleteDir(Configuration conf,String out_Dir) throws URISyntaxException, IOException {
        FileSystem fileSystem = FileSystem.get(new URI(out_Dir),conf);
        if(fileSystem.exists(new Path(out_Dir))){
            fileSystem.delete(new Path(out_Dir),true);
        }

    }
	public static class MyMapper extends Mapper<LongWritable,Text,Text,LongWritable> {
    //<k1,v1>是<0,hello you>,<10,me>
        Text k2 = new Text();
        LongWritable V2 = new LongWritable();
        protected void map(LongWritable key,Text value,Mapper<LongWritable,Text,Text,LongWritable>.Context context) throws IOException, InterruptedException {
            String[] words = value.toString().split("\t");
            for (String word:words){
                //word表示每一行中的每个单词，即K2
                k2.set(word);
                V2.set(1l);
                context.write(k2,V2);
            }
        }
    }
    //map函数执行完的输出结果是<k2,v2>是<hello,1>
    //排序候的结果是<hello,1><hello,1><me,1>
    //分组后的结果是<hello,{1,1}><me,1>

    //<k3,v2>是<hello,2><me,1>
    public static  class MyReduce extends Reducer<Text,LongWritable,Text,LongWritable>{
	    LongWritable v3 = new LongWritable();
    protected void reduce(Text k2,Iterable<LongWritable> v2s,Reducer<Text,LongWritable,Text,LongWritable>.Context context) throws IOException, InterruptedException {
        long count = 0l;
        for (LongWritable v2: v2s){
            count += v2.get();
        }
        v3.set(count);
        context.write(k2,v3);

    }
    }

}