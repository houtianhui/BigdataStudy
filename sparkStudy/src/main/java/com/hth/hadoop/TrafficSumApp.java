package com.hth.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TrafficSumApp {
	//<k1,v1>
	//<k2,v2>是手机号、单次通信的流量
	//<k3,v3>是手机号、流量汇总
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, TrafficSumApp.class.getSimpleName());
		job.setJarByClass(TrafficSumApp.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.1.213:9000/traffic"));
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(TrafficMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(TrafficWritable.class);
		
		job.setReducerClass(TrafficReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TrafficWritable.class);
		
		String OUT_DIR = "hdfs://192.168.1.213:9000/out1";
		FileOutputFormat.setOutputPath(job, new Path(OUT_DIR));
		job.setOutputFormatClass(TextOutputFormat.class);
		
		deleteOutDir(conf, OUT_DIR);
		
		job.waitForCompletion(true);
	}

	private static class TrafficMapper extends Mapper<LongWritable, Text, Text, TrafficWritable> {
		
		Text k2 = new Text();
		TrafficWritable v2 = new TrafficWritable();
		@Override
		protected void map(
				LongWritable key,
				Text value,
				Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] splited = line.split("\t");
			
			k2.set(splited[1]);
			v2.set(splited[6], splited[7], splited[8], splited[9]);
			
			context.write(k2, v2);
		}
	}
	
	private static class TrafficReducer extends Reducer<Text, TrafficWritable, Text, TrafficWritable> {
		
		TrafficWritable v3 = new TrafficWritable();
		@Override
		protected void reduce(
				Text k2,
				Iterable<TrafficWritable> v2s,
				Context context)
				throws IOException, InterruptedException {
			
			long t1 = 0L;
			long t2 = 0L;
			long t3 = 0L;
			long t4 = 0L;
			
			for (TrafficWritable v2 : v2s) {
				t1 += v2.t1;
				t2 += v2.t2;
				t3 += v2.t3;
				t4 += v2.t4;
			}
			
			v3.set(t1, t2, t3, t4);
			
			context.write(k2, v3);
		}
	}
	
	private static class TrafficWritable implements Writable {
		public long t1;
		public long t2;
		public long t3;
		public long t4;
		
		
		
		public void write(DataOutput out) throws IOException {
			out.writeLong(t1);
			out.writeLong(t2);
			out.writeLong(t3);
			out.writeLong(t4);
		}

		public void set(long t12, long t22, long t32, long t42) {
			this.t1 = t12;
			this.t2 = t22;
			this.t3 = t32;
			this.t4 = t42;
		}

		public void set(String string1, String string2, String string3,
				String string4) {
			this.t1 = Long.parseLong(string1);
			this.t2 = Long.parseLong(string2);
			this.t3 = Long.parseLong(string3);
			this.t4 = Long.parseLong(string4);
		}

		public void readFields(DataInput in) throws IOException {
			this.t1 = in.readLong();
			this.t2 = in.readLong();
			this.t3 = in.readLong();
			this.t4 = in.readLong();
		}
		
		@Override
		public String toString() {
			return this.t1+"\t"+this.t2+"\t"+this.t3+"\t"+this.t4;
		}
	}
	

	public static void deleteOutDir(Configuration conf, String OUT_DIR)
			throws IOException, URISyntaxException {
		FileSystem fileSystem = FileSystem.get(new URI(OUT_DIR), conf);
		if(fileSystem.exists(new Path(OUT_DIR))){
			fileSystem.delete(new Path(OUT_DIR), true);
		}
	}
}
