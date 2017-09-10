package com.app.biller.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.biller.controller.FileController;

public final class BillerUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(BillerUtil.class);
	
	private BillerUtil() {		
	}

	public static String getDateStr(String date) {
		String formattedDate = "";
		try {
			DateFormat sourceDateFormat = new SimpleDateFormat("mm/dd/yyyy");
			Date parsedDate = sourceDateFormat.parse(date);
			DateFormat targetDateFormat = new SimpleDateFormat("yyyy-mm-dd");
			formattedDate = targetDateFormat.format(parsedDate);

		} catch (ParseException e) {
			logger.info(e.getStackTrace().toString());
		}
		return formattedDate;
	}
}
