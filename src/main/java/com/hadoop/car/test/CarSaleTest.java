package com.hadoop.car.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.hadoop.car.mapper.CarSaleMapper;

import com.hadoop.car.reducer.CarSaleReducer;

public class CarSaleTest {

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			Job job=Job.getInstance(conf);
			job.setJarByClass(CarSaleTest.class);
			job.setMapperClass(CarSaleMapper.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			job.setReducerClass(CarSaleReducer.class);  
			FileInputFormat.setInputPaths(job,new Path("hdfs://192.168.38.120:9000/guazi1.csv"));
			FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.38.120:9000/guaziTest"));
			job.waitForCompletion(true);
				
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
