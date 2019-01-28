package com.hadoop.car.mapper;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.hadoop.car.Utils.ProvincesUtil;

public class CarSaleMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		String carlines[]=value.toString().split(",");
		String province = carlines[10];
		String k=ProvincesUtil.getProvince(province);
		context.write(new Text(k), new IntWritable(1));
	}

}
