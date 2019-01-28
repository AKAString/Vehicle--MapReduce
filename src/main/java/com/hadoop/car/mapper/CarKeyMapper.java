package com.hadoop.car.mapper;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.hadoop.car.Utils.DateUtils;
import com.hadoop.car.Utils.ProvincesUtil;
import com.hadoop.car.dimention.CarKeyDimention;
import com.hadoop.car.dimention.CarValueDimention;
import com.hadoop.qr.contstants.CarLogType;

public class CarKeyMapper extends Mapper<NullWritable, Text, CarKeyDimention, CarValueDimention> {
	@Override
	protected void map(NullWritable key, Text value,
			Mapper<NullWritable, Text, CarKeyDimention, CarValueDimention>.Context context)
			throws IOException, InterruptedException {
		String carlines[]=value.toString().split(",");
		if(carlines.length>1) {
			String carSaleDate=DateUtils.toYear(carlines[28]);
			String province=carlines[10];	
			/**
			 * 计算销售额
			 */
			//String sumprice=carlines[8];		
			//key
			CarKeyDimention carKeyDimentionAllSale = new CarKeyDimention();
			carKeyDimentionAllSale.getCarDateDimention().setFlag(CarLogType.YEARTYPE);
			carKeyDimentionAllSale.getCarDateDimention().setYear(carSaleDate);
			carKeyDimentionAllSale.setProvince(ProvincesUtil.getProvince(province));
			carKeyDimentionAllSale.setFlag(CarLogType.PRICETYPE);
			CarValueDimention carValueDimentionAllSale = new CarValueDimention();
			carValueDimentionAllSale.setFlag(CarLogType.SALECOUNTTYPE);
			carValueDimentionAllSale.setSalecount(1);
			carValueDimentionAllSale.setFlag("1");

			//value
			/*CarValueDimention carValueDimentionAllSale =new CarValueDimention();
			
			if(sumprice.endsWith("万")) {
				carValueDimentionAllSale.setSumPrice(Double.parseDouble(sumprice.substring(0,sumprice.length()-1)));
				
			}
			if(sumprice.endsWith("元")) {
				carValueDimentionAllSale.setSumPrice(Double.parseDouble(sumprice.substring(0,sumprice.length()-4))/10000);*/
			context.write(carKeyDimentionAllSale,carValueDimentionAllSale);
			
		}
	}
}

