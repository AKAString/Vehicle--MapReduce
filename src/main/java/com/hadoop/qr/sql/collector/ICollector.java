package com.hadoop.qr.sql.collector;

import java.sql.PreparedStatement;

import org.apache.hadoop.io.IntWritable;

import com.hadoop.car.dimention.CarKeyDimention;
import com.hadoop.car.dimention.CarValueDimention;

/**
 * 用来为sql语句赋值
 * @author t430
 */
public interface ICollector {
	public void process(CarKeyDimention key, CarValueDimention value,PreparedStatement ps);
}
