/**
 * 
 */
package com.lxl.common.component.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lxl.common.component.base.constant.DateFormatConstant;
import com.lxl.common.component.base.constant.NumberConstants;

/**
 * @author Administrator
 *
 */
public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	private static final ThreadLocal<DateFormat> DATE_FORMAT_LOCAL = new ThreadLocal<DateFormat>();

	/**
	 * 获取当天时间加1天
	 * 
	 * @return
	 */
	public static Date getTomorrowDate() {
		return addDays(getCurrentDate(), 1);
	}

	/**
	 * 获取当天时间加1天
	 * 
	 * @return
	 */
	public static Long getTomorrowCurrentTime() {
		return addDays(getCurrentDate(), 1).getTime();
	}

	/**
	 * 获取明天时间
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getTomorrowTime(String dateFormat) {
		DateFormat format = getDateFormat(dateFormat);
		Date currentTime = getCurrentDate();
		try {
			return format.format(currentTime);
		} finally {
			DATE_FORMAT_LOCAL.remove();
		}
	}

	/**
	 * 获取昨天时间
	 * 
	 * @return
	 */
	public static Date getYesterdayDate() {
		return addDays(getCurrentDate(), -1);
	}

	/**
	 * 获取昨天时间
	 * 
	 * @return
	 */
	public static Long getYesterdayCurrentTime() {
		return addDays(getCurrentDate(), -1).getTime();
	}

	/**
	 * 获取昨天时间
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getYesterdayTime(String dateFormat) {
		DateFormat format = getDateFormat(dateFormat);
		Date currentTime = getYesterdayDate();
		try {
			return format.format(currentTime);
		} finally {
			DATE_FORMAT_LOCAL.remove();
		}
	}

	/**
	 * 获取当天时间
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static Date getCurrentDates(String dateFormat) {
		String date = getCurrentTime(dateFormat);
		return DateUtils.parseDate(date, dateFormat);
	}

	/**
	 * 获取当前时间
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取当前时间
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static Long getCurrentTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取当天时间
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getCurrentTime(String dateFormat) {
		DateFormat format = getDateFormat(dateFormat);
		Date currentTime = getCurrentDate();
		try {
			return format.format(currentTime);
		} finally {
			DATE_FORMAT_LOCAL.remove();
		}
	}

	private static DateFormat getDateFormat(String format) {
		DateFormat df = DATE_FORMAT_LOCAL.get();
		if (df == null) {
			df = new SimpleDateFormat(format);
			DATE_FORMAT_LOCAL.set(df);
		}
		return df;
	}

	public static Date parseDate(String dateStr, String format) {
		try {
			return getDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			logger.error("parseDate error: dateStr={}, format={}", dateStr, format, e);
			throw new RuntimeException("parseDate error");
		} finally {
			DATE_FORMAT_LOCAL.remove();
		}
	}

	public static String formatDateTime(Date date, String format) {
		return getDateFormat(format).format(date);
	}

	/**
	 * 加天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDays(Date date, Integer day) {
		if (day == null) {
			day = NumberConstants.ZERO_INT;
		}
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历
		cal.setTime(date);
		cal.add(Calendar.DATE, +day);
		return cal.getTime();
	}

	/**
	 * 加小时数
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHours(Date date, Integer hour) {
		if (hour == null || "".equals(hour)) {
			hour = NumberConstants.ZERO_INT;
		}
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历
		cal.setTime(date);
		cal.add(Calendar.HOUR, +hour);
		return cal.getTime();
	}

	/**
	 * 加分钟数
	 * 
	 * @param date
	 * @param mins
	 * @return
	 */
	public static Date addMinutes(Date date, Integer mins) {
		if (mins == null || "".equals(mins)) {
			mins = NumberConstants.ZERO_INT;
		}
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历
		cal.setTime(date);
		cal.add(Calendar.MINUTE, +mins);
		return cal.getTime();
	}

	/**
	 * 加秒数
	 * 
	 * @param date
	 * @param secs
	 * @return
	 */
	public static Date addSeconds(Date date, Integer secs) {
		if (secs == null || "".equals(secs)) {
			secs = NumberConstants.ZERO_INT;
		}
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历
		cal.setTime(date);
		cal.add(Calendar.SECOND, +secs);
		return cal.getTime();
	}

	/**
	 * 是否大于 d1
	 * 
	 * @param d1
	 * @return
	 */
	public static boolean afterNow(Date d1) {
		return before(d1, getCurrentDate());
	}

	/**
	 * 是否小于 d1
	 * 
	 * @param d1
	 * @return
	 */
	public static boolean beforeNow(Date d1) {
		return after(d1, getCurrentDate());
	}

	/**
	 * 是否等于当前时间
	 * 
	 * @param d1
	 * @return
	 */
	public static boolean equalNow(Date d1) {
		return equal(d1, getCurrentDate());
	}

	/**
	 * d1大于d2则返回true，否则返回false
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean after(Date d1, Date d2) {
		int result = d1.compareTo(d2);
		if (result > NumberConstants.ZERO_INT) {
			return true;
		}
		return false;
	}

	/**
	 * d1小于d2则返回true，否则返回false
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean before(Date d1, Date d2) {
		int result = d1.compareTo(d2);
		if (result < NumberConstants.ZERO_INT) {
			return true;
		}
		return false;
	}

	/**
	 * d1等于d2则返回true，否则返回false
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean equal(Date d1, Date d2) {
		int result = d1.compareTo(d2);
		if (result == NumberConstants.ZERO_INT) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String strD1 = "2019-08-30 12:58:01";
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormatConstant.yyyy_MM_ddHHmmss);
		try {
			Date d1 = sdf.parse(strD1);
			Date result = addDays(d1, 2);
			System.out.println(formatDateTime(result, DateFormatConstant.yyyy_MM_ddHHmmss));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
