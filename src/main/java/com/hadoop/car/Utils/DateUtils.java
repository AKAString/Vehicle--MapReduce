package com.hadoop.car.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat MSTODATE = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat MSTOYEARMONTH = new SimpleDateFormat("yyyy/MM");
	private static SimpleDateFormat MSTOYEAR = new SimpleDateFormat("yyyy");
	

	public static String toYear(String ms) {
		try {
			return MSTOYEAR.format(MSTODATE.parse(ms));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String toYearMonth(String ms) {
		return MSTOYEARMONTH.format(new Date(Long.parseLong(ms.trim())));
	}
	
	
	/*public static String toYear(String ms) {
		return MSTOYEAR.format(new Date(Long.parseLong(ms.trim())));
	}*/
	/*public static String toSeason(String ms) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(Long.parseLong(ms)));
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int season = 0;
		if (month >= 1 && month <= 3) {
			season=1;
		}
		if (month >= 4 && month <= 6) {
			season=2;
		}
		if (month >= 7 && month <= 9) {
			season=3;
		}
		if (month >= 10 && month <= 12) {
			season=4;
		}		
		return String.valueOf(year+"-"+season);
	}*/
}
