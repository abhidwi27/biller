package com.ibm.biller.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillerUtil {

	public static String getDateStr(String date) {
		String formattedDateStr = "";
		try {
			DateFormat sourceDateFormat = new SimpleDateFormat("mm/dd/yyyy");
			Date parsedDate = sourceDateFormat.parse(date);
			DateFormat targetDateFormat = new SimpleDateFormat("yyyy-mm-dd");
			formattedDateStr = targetDateFormat.format(parsedDate);

		} catch (ParseException e) {
			System.out.println(e);
		}
		return formattedDateStr;
	}
}
