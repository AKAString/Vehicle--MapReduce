package com.hadoop.car.reducer;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapreduce.Reducer;

import com.hadoop.car.dimention.CarKeyDimention;
import com.hadoop.car.dimention.CarValueDimention;
import com.hadoop.qr.contstants.CarLogType;



public class CarAllSaleReducer extends Reducer<CarKeyDimention, CarValueDimention, CarKeyDimention, CarValueDimention> {
	@Override
	protected void reduce(CarKeyDimention key, Iterable<CarValueDimention> value,
			Reducer<CarKeyDimention, CarValueDimention, CarKeyDimention, CarValueDimention>.Context context)
			throws IOException, InterruptedException {
		/*if(key.getFlag().equals(CarLogType.PRICETYPE)) {
			int sum = 0;
			if(key.getCarDateDimention().getFlag().equals(CarLogType.YEARTYPE)) {
				Iterator<CarValueDimention> it = value.iterator();	
				while(it.hasNext()) {
					sum +=it.next().get();
				}
				CarValueDimention yearCarValueDimention = new CarValueDimention();
				yearCarValueDimention.setFlag(CarLogType.PRICETYPE);
				yearCarValueDimention.setSumPrice(sum);
				context.write(key,new IntWritable(sum));
			}*/
		if (key.getFlag().equals(CarLogType.PRICETYPE)) {
			int salecount = 0;
			Iterator<CarValueDimention> it = value.iterator();
			while (it.hasNext()) {
				CarValueDimention value1 = it.next();
				salecount += value1.getSalecount();
			}
			CarValueDimention salecountCarValueDimention = new CarValueDimention();
			salecountCarValueDimention.setFlag("1");
			salecountCarValueDimention.setSalecount(salecount);
			context.write(key,salecountCarValueDimention );
		}
		}
		
	}


