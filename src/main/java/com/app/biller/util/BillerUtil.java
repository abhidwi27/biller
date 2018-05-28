package com.app.biller.util;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public static <T> T[] joinArrayGeneric(T[]... arrays) {
        int length = 0;
        for (T[] array : arrays) {
            length += array.length;
        }

        //T[] result = new T[length];
        final T[] result = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), length);

        int offset = 0;
        for (T[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }
}
