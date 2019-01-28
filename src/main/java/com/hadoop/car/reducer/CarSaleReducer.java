package com.hadoop.car.reducer;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CarSaleReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	@Override
	protected void reduce(Text key, Iterable<IntWritable> value,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		Iterator<IntWritable> it = value.iterator();
		int sum=0;
		while(it.hasNext()) {
			sum +=it.next().get();
		}
		context.write(key, new IntWritable(sum));
	}

}
