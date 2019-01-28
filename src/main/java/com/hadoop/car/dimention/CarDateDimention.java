package com.hadoop.car.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.hadoop.qr.contstants.CarLogType;

public class CarDateDimention extends BaseDimention {
	
	private String year;
	private String flag;

	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public CarDateDimention() {
		// TODO Auto-generated constructor stub
		
		this.year = "";
		this.flag = "";

	}

	@Override
	public void write(DataOutput out) throws IOException {
		
		out.writeUTF(year);
		out.writeUTF(flag);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		
		this.year = in.readUTF();
		this.flag = in.readUTF();

	}

	@Override
	public int compareTo(BaseDimention o) {
		CarDateDimention carDateDimention = (CarDateDimention) o;
		int tmp = this.year.compareTo(carDateDimention.year);
		if (tmp != 0) {
			return tmp;
		}
		tmp = flag.compareTo(carDateDimention.flag);

		if (tmp != 0) {
			return tmp;
		}
		return 0;
	}

	@Override
	public String toString() {
		String str = "";
		if (CarLogType.YEARTYPE.equals(flag)) {
			str = this.year +  "\t" + this.flag;
		}
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CarDateDimention carDateDimention = (CarDateDimention) obj;
		if (!this.year.equals(carDateDimention.year)||!this.flag.equals(carDateDimention.flag)) {
			return false;
		}
		return true;
	}

}
