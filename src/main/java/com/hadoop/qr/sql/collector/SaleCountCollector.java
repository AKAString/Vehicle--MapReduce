package com.hadoop.qr.sql.collector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.io.IntWritable;

import com.hadoop.car.dimention.CarKeyDimention;
import com.hadoop.car.dimention.CarValueDimention;

public class SaleCountCollector implements ICollector {

	/*
	 * INSERT INTO car_salecount( saledate, dateflag, province, salecount) VALUES(?,
	 * ?, ?,?) ON DUPLICATE KEY UPDATE saledate = ?,dateflag = ?,province = ?
	 */

	@Override
	public void process(CarKeyDimention key, CarValueDimention value, PreparedStatement ps) {
		try {
			ps.setString(1, key.getCarDateDimention().getYear());
			ps.setString(2, key.getCarDateDimention().getFlag());
			ps.setString(3, key.getProvince());
			ps.setInt(4, value.getSalecount());
			ps.setString(5, key.getCarDateDimention().getYear());
			ps.setString(6, key.getCarDateDimention().getFlag());
			ps.setString(7, key.getProvince());
			ps.addBatch();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
