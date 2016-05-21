/**
 * Created on 2016年4月5日
 */
package org.waddys.xcloud.common.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能名: 日期转换工具类 功能描述: 日期转换公共操作
 * 
 * Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 */
public class DateUtil {
	private static String defaultDatePattern = "yyyy-MM-dd";

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return defaultDatePattern;
	}

	/**
	 * 返回预设Format的当前日期字符串
	 */
	public static String getToday() {
		Date today = new Date();
		return format(today);
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 */
	public static String format(Date date) {
		return date == null ? " " : format(date, getDatePattern());
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(Date date, String pattern) {
		return date == null ? " " : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parse(String strDate) throws ParseException {
		return StringUtils.isNotBlank(strDate) ? parse(strDate, getDatePattern()) : null;
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(String strDate, String pattern) throws ParseException {
		return StringUtils.isNotBlank(strDate) ? new SimpleDateFormat(pattern).parse(strDate) : null;
	}

	/**
	 * 在日期上增加数个整月
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	public static String getLastDayOfMonth(String year, String month) {
		Calendar cal = Calendar.getInstance();
		// 年
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		// 月，因为Calendar里的月是从0开始，所以要-1
		// cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		// 月份加一，得到下个月的一号
		cal.add(Calendar.MONTH, 1);
		// 下一个月减一为本月最后一天
		cal.add(Calendar.DATE, -1);
		return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获得月末是几号
	}

	public static Date getDate(String year, String month, String day) throws ParseException {
		String result = year + "- " + (month.length() == 1 ? ("0 " + month) : month) + "- "
				+ (day.length() == 1 ? ("0 " + day) : day);
		return parse(result);
	}

	public static int getIntervalDayTime(String startTime, String endTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date now = df.parse(startTime);
		java.util.Date date = df.parse(endTime);
		long l = getIntervalTime(date, now);
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		// long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		return (int) day;
	}

	public static int getIntervalHourTime(String startTime, String endTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date now = df.parse(startTime);
		java.util.Date date = df.parse(endTime);
		long l = getIntervalTime(date, now);
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		// long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		return (int) hour;
	}

	public static int getIntervalMinTime(String startTime, String endTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date now = df.parse(startTime);
		java.util.Date date = df.parse(endTime);
		long l = getIntervalTime(date, now);
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
	    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		return (int) min;
	}

	public static long getIntervalTime(Date statDate, Date endDate) {
		return endDate.getTime() - statDate.getTime();
	}
}