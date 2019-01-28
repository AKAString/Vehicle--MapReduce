package com.hadoop.car.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

import com.hadoop.qr.contstants.CarLogType;

public class CarValueDimention implements Writable {
	private double sumPrice; 
	private String flag;
	private int salecount;
	public double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(double d) {
		this.sumPrice = d;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public int getSalecount() {
		return salecount;
	}

	public void setSalecount(int salecount) {
		this.salecount = salecount;
	}
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(sumPrice);
		out.writeInt(salecount);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.sumPrice=in.readDouble();
		this.salecount=in.readInt();
		
	}

	@Override
	public String toString() {
		if(CarLogType.PRICETYPE.equals(getFlag())) {
		return this.sumPrice+" "+"万";
	}
		if("1".equals(getFlag())) {
			return "\t"+salecount+"";
		}
		return null;
}
}
