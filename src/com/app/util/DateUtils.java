package com.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jfinal.kit.StrKit;

/**
 * 
 * 日期,时间工具类
 */
public class DateUtils {

	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public final static long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
	
	public static String getCurrDate(){
		return getCurrDateFormat(DATE_FORMAT);
	}
	
	public static String getCurrDateTime(){
		return getCurrDateFormat(DATETIME_FORMAT);
	}
	
	/**
	 * 按照指定格式获取 当前日期
	 * @param format
	 * @return
	 */
	public static String getCurrDateFormat(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	public static String getDateFormat( Date dateTime){
		return getDateFormat(DATE_FORMAT, dateTime);
	}
	
	public static String getDateTimeFormat( Date dateTime){
		return getDateFormat(DATETIME_FORMAT, dateTime);
	}
	/**
	 * 按照指定格式(format) 获取 指定日期(dateTime)
	 * @param format
	 * @param dateTime
	 * @return
	 */
	public static String getDateFormat(String format, String dateTime){
		Date date = parseDateTime(dateTime);
		return getDateFormat(format, date);
	}
	public static String getDateFormat(String format, Date dateTime){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(dateTime);
	}
	public static long getDiffMills(Date bDate, Date eDate){
		return eDate.getTime() - bDate.getTime();
	}
	/**
	 * 获取 当前日期到 指定日期的 时间段, 并自动根据时间间隔返回 结果
	 * @param dateTime
	 * @return
	 */
	public static String getDiffDate(Date dateTime){
		String times = "";
		Date date = dateTime;	// 指定日期
		Date currDate = new Date();				// 当前日期
		
		long time = date.getTime();
		long currTime = currDate.getTime();
		long diff = (currTime -time)/1000;
		if(diff < 60){
			times = diff+"秒前";
		}else if((diff /= 60) < 60){
			times = diff + "分前";
		}else if((diff /= 60) < 24){
			times = diff + "小时前";
		}else if((diff /= 24) < 30){
			if(diff == 1){
				times = "昨天("+getDateFormat("HH:mm",dateTime)+")";
			}else{
				times = diff + "天前";
			}
		}else{
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date);
			cal2.setTime(currDate);
			
			int years = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
			int months = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
			if( years <= 0){
				times = months + "月前"; 
			}else{
				times = years + "年前";
			}
		}
		return times;
	}
	/**
	 * Get the previous time, from how many days to now.
	 * @param days
	 * @return The new previous time.
	 */
	public static Date previous(int days) {
		return new Date(System.currentTimeMillis() - days * 3600000L * 24L);
	}

	/**
	 * Parse date like "yyyy-MM-dd".
	 */
	public static Date parseDate(String d) {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Parse date and time like "yyyy-MM-dd hh:mm:ss".
	 */
	public static Date parseDateTime(String dt) {
		try {
			return new SimpleDateFormat(DATETIME_FORMAT).parse(dt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 转换日期字符串得到指定格式的日期类型
	 * 
	 * @param formatString
	 *            需要转换的格式字符串
	 * @param targetDate
	 *            需要转换的时间
	 * @return
	 * @throws ParseException
	 */
	public static final Date parseDate(String format,
			String targetDate) throws ParseException {
		if (StrKit.isBlank(targetDate))
			return null;
		SimpleDateFormat sdf = null;
		Date result = null;
		sdf = new SimpleDateFormat(format);
		try {
			result = sdf.parse(targetDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return result;
	}

	/**
	 * 日期比较
	 * 
	 * 判断时间date1是否在时间date2之前 <br/>
	 * 时间格式 2005-4-21 16:16:34 <br/>
	 * 添加人：胡建国
	 * 
	 * @param targetDate
	 * @return
	 */
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 日期比较
	 * 
	 * 判断当前时间是否在时间date2之前 <br/>
	 * 时间格式 2005-4-21 16:16:34 <br/>
	 * 添加人：胡建国
	 * 
	 * @param targetDate
	 * @return
	 */
	public static boolean isDateBefore(String date2) {
		if (date2 == null) {
			return false;
		}
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}


	/**
	 * 获取上个月开始时间
	 * 
	 * @param currentDate
	 *            当前时间
	 * @return 上个月的第一天
	 */
	public static Date getBoferBeginDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), (currentDate
				.get(Calendar.MONTH)) - 1, result
				.getActualMinimum(Calendar.DATE), 0, 0, 0);
		return result.getTime();
	}

	/**
	 * 获取指定月份的第一天
	 * 
	 * @param currentDate
	 * @return
	 */
	public static Date getBeginDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), (currentDate
				.get(Calendar.MONTH)), result.getActualMinimum(Calendar.DATE));
		return result.getTime();
	}


	/**
	 * 获取上个月结束时间
	 * 
	 * @param currentDate
	 *            当前时间
	 * @return 上个月最后一天
	 */
	public static Date getBoferEndDate(Calendar currentDate) {
		Calendar result = currentDate;
		// result.set(currentDate.get(Calendar.YEAR), currentDate
		// .get(Calendar.MONTH) - 1);
		result.set(Calendar.DATE, 1);
		result.add(Calendar.DATE, -1);
		return result.getTime();
	}

	/**
	 * 获取两个时间的时间间隔
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static int getDaysBetween(Calendar beginDate, Calendar endDate) {
		if (beginDate.after(endDate)) {
			Calendar swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int days = endDate.get(Calendar.DAY_OF_YEAR)
				- beginDate.get(Calendar.DAY_OF_YEAR) + 1;
		int year = endDate.get(Calendar.YEAR);
		if (beginDate.get(Calendar.YEAR) != year) {
			beginDate = (Calendar) beginDate.clone();
			do {
				days += beginDate.getActualMaximum(Calendar.DAY_OF_YEAR);
				beginDate.add(Calendar.YEAR, 1);
			} while (beginDate.get(Calendar.YEAR) != year);
		}
		return days;
	}

	/**
	 * 获取两个时间的时间间隔(月份)
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static int getMonthsBetween(Date beginDate, Date endDate) {
		if (beginDate.after(endDate)) {
			Date swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int months = endDate.getMonth() - beginDate.getMonth();
		int years = endDate.getYear() - beginDate.getYear();

		months += years * 12;

		return months;
	}

	/**
	 * 获取两个时间内的工作日
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static int getWorkingDay(Calendar beginDate, Calendar endDate) {
		int result = -1;
		if (beginDate.after(endDate)) {
			Calendar swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int charge_start_date = 0;
		int charge_end_date = 0;
		int stmp;
		int etmp;
		stmp = 7 - beginDate.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - endDate.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {
			charge_start_date = stmp - 1;
		}
		if (etmp != 0 && etmp != 6) {
			charge_end_date = etmp - 1;
		}
		result = (getDaysBetween(getNextMonday(beginDate),
				getNextMonday(endDate)) / 7)
				* 5 + charge_start_date - charge_end_date;
		return result;
	}

	/**
	 * 根据当前给定的日期获取当前天是星期几(中国版的)
	 * 
	 * @param date
	 *            任意时间
	 * @return
	 */
	public static String getChineseWeek(Calendar date) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayOfWeek - 1];

	}

	/**
	 * 获得日期的下一个星期一的日期
	 * 
	 * @param date
	 *            任意时间
	 * @return
	 */
	public static Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	/**
	 * 获取两个日期之间的休息时间
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static int getHolidays(Calendar beginDate, Calendar endDate) {
		return getDaysBetween(beginDate, endDate)
				- getWorkingDay(beginDate, endDate);

	}

	public static boolean isDateEnable(Date beginDate, Date endDate,
			Date currentDate) {
		// 开始日期
		long beginDateLong = beginDate.getTime();
		// 结束日期
		long endDateLong = endDate.getTime();
		// 当前日期
		long currentDateLong = currentDate.getTime();
		if (currentDateLong >= beginDateLong && currentDateLong <= endDateLong) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 获取当前月份的第一天
	 * 
	 * @param currtenDate
	 *            当前时间
	 * @return
	 */
	public static Date getMinDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), currentDate
				.get(Calendar.MONTH), currentDate
				.getActualMinimum(Calendar.DATE));
		return result.getTime();
	}

	public static Calendar getDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date);
		return calendar;
	}

	public static Calendar getDate(int year, int month) {
		return getDate(year, month, 0);
	}

	public static Date getCountMinDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, calendar.getActualMinimum(Calendar.DATE));
		return calendar.getTime();
	}

	public static Date getCountMaxDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), 0);
		return calendar2.getTime();
	}

	/**
	 * 获取当前月份的第一天
	 * 
	 * @param currtenDate
	 *            当前时间
	 * @return
	 */
	public static Date getMinDate() {
		Calendar currentDate = Calendar.getInstance();
		return getMinDate(currentDate);
	}

	/**
	 * 获取当前月分的最大天数
	 * 
	 * @param currentDate
	 *            当前时间
	 * @return
	 */
	public static Date getMaxDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), currentDate
				.get(Calendar.MONTH), currentDate
				.getActualMaximum(Calendar.DATE));
		return result.getTime();
	}

	/**
	 * 获取当前月分的最大天数
	 * 
	 * @param currentDate
	 *            当前时间
	 * @return
	 */
	public static Date getMaxDate() {
		Calendar currentDate = Calendar.getInstance();
		return getMaxDate(currentDate);
	}

	/**
	 * 获取日期最大的时间
	 * 
	 * @return
	 */
	public static Date getMaxDateTimeForToDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar
				.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		return calendar.getTime();
	}

	/**
	 * 获取 date 最小时间
	 * 
	 * @return
	 */
	public static Date getMinDateTimeForToDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar
				.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		return calendar.getTime();
	}

	/**
	 * 获取发生日期的结束时间 根据用户设置的日期天数来判定这这个日期是什么(例如 (getHappenMinDate = 2008-10-1) 的话
	 * 那么 (getHappenMaxDate = 2008-11-1) 号)
	 * 
	 * @return
	 */
	public static Date getHappenMaxDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 加减天数
	 * 
	 * @param num
	 * @param Date
	 * @return
	 */
	public static Date addDay(int num, Date Date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Date);
		calendar.add(Calendar.DATE, num);// 把日期往后增加 num 天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println(getMaxDateTimeForToDay());
	 * System.out.println(getMinDateTimeForToDay()); }
	 */

	/**
	 * 计算两端时间的小时差
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getHour(Date begin, Date end) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(begin);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(end);
		Long millisecond = c2.getTimeInMillis() - c1.getTimeInMillis();
		Long hour = millisecond / 1000 / 60 / 60;
		Long minute = (millisecond / 1000 / 60) % 60;
		if (minute >= 30) {
			hour++;
		}

		return hour.intValue();
	}
	
	

	/*****************************************
	 * @功能 计算某年某月的结束日期
	 * @return interger
	 * @throws ParseException
	 ****************************************/
	public static String getYearMonthEndDay(int yearNum, int monthNum)
			throws ParseException {

		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "31";
		if (tempMonth.equals("1") || tempMonth.equals("3")
				|| tempMonth.equals("5") || tempMonth.equals("7")
				|| tempMonth.equals("8") || tempMonth.equals("10")
				|| tempMonth.equals("12")) {
			tempDay = "31";
		}
		if (tempMonth.equals("4") || tempMonth.equals("6")
				|| tempMonth.equals("9") || tempMonth.equals("11")) {
			tempDay = "30";
		}
		if (tempMonth.equals("2")) {
			if (isLeapYear(yearNum)) {
				tempDay = "29";
			} else {
				tempDay = "28";
			}
		}
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return tempDate;// setDateFormat(tempDate,"yyyy-MM-dd");
	}

	/*****************************************
	 * @功能 判断某年是否为闰年
	 * @return boolean
	 * @throws ParseException
	 ****************************************/
	public static boolean isLeapYear(int yearNum) {
		boolean isLeep = false;
		/** 判断是否为闰年，赋值给一标识符flag */
		if ((yearNum % 4 == 0) && (yearNum % 100 != 0)) {
			isLeep = true;
		} else if (yearNum % 400 == 0) {
			isLeep = true;
		} else {
			isLeep = false;
		}
		return isLeep;
	}


}
