package com.oa.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * 
 * @author ruochenxing
 * @date 2014-8-15
 */
public class DateUtil {
	/**
	 * 获取某段时间的所有月份
	 */
	public static List<String> getMonthList(Date begin, Date end) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat monthFormat = null;
		if (begin.getYear() == end.getYear()) {
			monthFormat = new SimpleDateFormat("MM");
		} else {
			monthFormat = new SimpleDateFormat("yyyy/MM");
		}
		List<String> monthList = new ArrayList<String>();
		int months = (end.getYear() - begin.getYear()) * 12
				+ (end.getMonth() - begin.getMonth());
		for (int i = 0; i <= months; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(begin);
			calendar.add(Calendar.MONTH, i);
			monthList.add(monthFormat.format(calendar.getTime()));
		}
		return monthList;
	}

	/**
	 * 获取本周第一天
	 */
	public static Date firstDayInCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date start = cal.getTime();
		return start;
	}

	/**
	 * 获取本周最后一天
	 */
	public static Date lastDayInCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		Date end = cal.getTime();
		return end;
	}

	/**
	 * 获取本月第一天
	 */
	public static Date firstDayInCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date first = cal.getTime();
		return first;
	}

	/**
	 * 获取本月最后一天
	 */
	public static Date lastDayInCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		Date lastDay = cal.getTime();
		return lastDay;
	}

	/**
	 * 获取今年第一天
	 */
	public static Date firstDayInCurrYear() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 0);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		Date firstDay = cal.getTime();
		return firstDay;
	}

	/**
	 * 获取今年最后一天
	 */
	public static Date lastDayInCurrYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal
				.getActualMaximum(Calendar.DAY_OF_YEAR));
		Date firstDay = cal.getTime();
		return firstDay;
	}

	/**
	 * 获取昨天的日期
	 */
	public static Date yestoday(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, -1);
		Date d = cal.getTime();
		return d;
	}

	/**
	 * 获取下周第一天
	 */
	public static Date getNextWeekFirstDay() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day != Calendar.SUNDAY)
			cal.add(Calendar.WEEK_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 获取下周最后一天
	 */
	public static Date getNextWeekLastDay() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day != Calendar.SUNDAY)
			cal.add(Calendar.WEEK_OF_MONTH, 2);
		else
			cal.add(Calendar.WEEK_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return cal.getTime();
	}

	/**
	 * 获取明天的日期
	 */
	public static Date tomorrow(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, +1);
		Date d = cal.getTime();
		return d;
	}

	/**
	 * 获取两个日期相差的年月日
	 */
	public static int[] getDateLength(Date begin, Date end) {
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		c1.setTime(begin);
		c2.setTime(end);
		int[] result = new int[3];
		result[0] = (c2.get(java.util.Calendar.YEAR) - c1
				.get(java.util.Calendar.YEAR)) < 0 ? 0 : c2
				.get(java.util.Calendar.YEAR)
				- c1.get(java.util.Calendar.YEAR);
		result[1] = (c2.get(java.util.Calendar.MONTH) - c1
				.get(java.util.Calendar.MONTH)) < 0 ? 0 : c2
				.get(java.util.Calendar.MONTH)
				- c1.get(java.util.Calendar.MONTH);
		result[2] = (c2.get(java.util.Calendar.DAY_OF_MONTH) - c1
				.get(java.util.Calendar.DAY_OF_MONTH)) < 0 ? 0 : c2
				.get(java.util.Calendar.DAY_OF_MONTH)
				- c1.get(java.util.Calendar.DAY_OF_MONTH);
		return result;
	}

	static String format(int[] result) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < result.length - 1; i++)
			sb.append(result[i] + ",");
		sb.append(result[result.length - 1] + "]");
		return sb.toString();
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByDate(Date date) {
		Calendar a = Calendar.getInstance();
		a.setTime(date);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static void main(String[] args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		String d = "2014-10";
		Date date = df.parse(d);
		System.out.println(getDaysByDate(date));
	}
	/**
	 * 获取某个日期所在月份的所有天数
	 * format:2014-09-12
	 * */
	public static Integer[] getMonthDays(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) - 1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		Integer[] days = new Integer[maxDay];
		for (int i = 1; i <= maxDay; i++) {
			days[i - 1] = i;
		}
		return days;
	}
}
