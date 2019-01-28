package com.hadoop.car.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.hadoop.car.dimention.CarKeyDimention;
import com.hadoop.car.dimention.CarValueDimention;
import com.hadoop.car.mapper.CarKeyMapper;
import com.hadoop.car.output.format.MysqlCarFormat;
import com.hadoop.car.reducer.CarAllSaleReducer;
import com.hadoop.qr.input.format.SmallFileCombieFormat;

public class CarAllSaleTest implements Tool {
	public static void main(String[] args) {
		// 用于加载配置文件
		Configuration conf = new Configuration();
		conf.setLong("mapreduce.input.fileinputformat.split.maxsize", 100 * 1024 * 1024);
		try {
			int result = ToolRunner.run(conf, new CarAllSaleTest(), args);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Configuration conf;

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
		this.conf.addResource("jdbc.xml");
		this.conf.addResource("output-collector.xml");
		this.conf.addResource("query-mapping.xml");
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public int run(String[] args) throws Exception {
		args = new String[] { "d://guazi.csv", "d://guaziTest" };
		if (args.length != 2) {
			System.out.println("请传递输入路径和输出路径!!!");
			return 0;
		}
		Job job = Job.getInstance(getConf());
		job.setJarByClass(CarAllSaleTest.class);
		job.setInputFormatClass(SmallFileCombieFormat.class);
		job.setMapperClass(CarKeyMapper.class);
		job.setReducerClass(CarAllSaleReducer.class);
		job.setOutputKeyClass(CarKeyDimention.class);
		job.setOutputValueClass(CarValueDimention.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		//FileOutputFormat.setOutputPath(job, new Path(args[1])); 
		job.setOutputFormatClass(MysqlCarFormat.class);
		if (job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

}
