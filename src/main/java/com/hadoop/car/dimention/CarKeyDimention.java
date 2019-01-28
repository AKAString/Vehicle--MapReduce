package com.hadoop.car.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.hadoop.qr.contstants.CarLogType;

public class CarKeyDimention extends BaseDimention {
	private String province;
	private String flag;
	private CarDateDimention carDateDimention;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public CarDateDimention getCarDateDimention() {
		return carDateDimention;
	}

	public void setCarDateDimention(CarDateDimention carDateDimention) {
		this.carDateDimention = carDateDimention;
	}

	public CarKeyDimention() {
		this.carDateDimention = new CarDateDimention();
		this.province = "";
		this.flag = "";
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(province);
		carDateDimention.write(out);
		out.writeUTF(flag);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.province = in.readUTF();
		this.carDateDimention.readFields(in);
		this.flag = in.readUTF();
	}

	@Override
	public int compareTo(BaseDimention o) {
		CarKeyDimention carKeyDimention = (CarKeyDimention) o;
		int tmp = this.flag.compareTo(carKeyDimention.flag);
		if (tmp != 0) {
			return tmp;
		}
		tmp = carDateDimention.compareTo(carKeyDimention.carDateDimention);
		if (tmp != 0) {
			return tmp;
		}
		tmp = province.compareTo(carKeyDimention.province);
		if (tmp != 0) {
			return tmp;
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CarKeyDimention carKeyDimention = (CarKeyDimention) obj;
		if (!this.flag.equals(carKeyDimention.flag) || !this.carDateDimention.equals(carKeyDimention.carDateDimention)
				|| !this.province.equals(carKeyDimention.province)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String str = "";
		if (CarLogType.PRICETYPE.equals(flag)) {
			str = this.carDateDimention.toString() + "\t" + this.province;
		}
		return str;
	}

}
