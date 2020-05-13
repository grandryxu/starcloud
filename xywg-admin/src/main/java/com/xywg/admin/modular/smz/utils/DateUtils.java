package com.xywg.admin.modular.smz.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xywg.admin.modular.report.service.impl.PersonJoinReportServiceImpl;

/**
 * 日期工具
 * 
 * @author duanfen
 */
public class DateUtils {
	private static Logger log = LoggerFactory.getLogger(DateUtils.class);
	// 一天的毫秒数 60*60*1000*24
	public final static long DAY_MILLIS = 86400000;

	// 一小时的毫秒数 60*60*1000
	private final static long HOUR_MILLIS = 3600000;

	// 一分钟的毫秒数 60*1000
	private final static long MINUTE_MILLIS = 60000;

	/** 默认时区名称 **/
	private final static String DEFAULT_TIME_ZONE = "Asia/Shanghai";

	private static final String[] dayOfWeekNames = { "Sun", "Mon", "Tue",
			"Wed", "Thu", "Fri", "Sat" };

	private static final String[] monthOfYearNames = { "Jan", "Feb", "Mar",
			"Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	// private static String[] parsePatterns = { "yyyy-MM-dd",
	// "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
	// "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "M月dd日EEEE" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据字符串格式日期，返回Date
	 * 
	 * @param d
	 *            Date
	 * @return 当前的系统日期
	 */
	public static Date parseDateString(String dateStr, String pattern) {
		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getInstance();
		int weekDay = 0;
		boolean isDateStr = false;
		for (; weekDay < dayOfWeekNames.length; weekDay++) {
			String wd = dayOfWeekNames[weekDay];
			if (dateStr.trim().startsWith(wd)) {
				dateStr = dateStr.trim().replace(wd, "");
				isDateStr = true;
				break;
			}
		}
		if (isDateStr) {
			try {
				int month = 0;
				for (; month < monthOfYearNames.length; month++) {
					String mon = monthOfYearNames[month];
					if (dateStr.trim().startsWith(mon)) {
						dateStr = dateStr.trim().replace(mon, "");
						break;
					}
				}
				// dow mon dd hh:mm:ss zzz yyyy
				sdf.applyPattern("dd HH:mm:ss zzz yyyy");
				Date newDate = sdf.parse(dateStr);
				Calendar c = Calendar.getInstance();
				c.setTime(newDate);
				c.set(Calendar.MONTH, month);
				return c.getTime();
			} catch (ParseException e) {
				log.error(e.getMessage());
				return null;
			}
		} else {
			return paresDate(dateStr, pattern);
		}
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 根据格式返回日期
	 * 
	 * @param parese
	 * @return
	 */
	public static String getDateParse(String parese) {
		return formatDate(new Date(), parese);
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		return parseDate(str.toString());
	}

	/**
	 * 解析日期字符串。 包含了解析java.util.Date toString()方法返回的字符串解析。
	 * 
	 * @param source
	 *            要解析的字符串资源。
	 * @param pattern
	 *            日期的字符串格式。
	 * @return 返回java.util.Date对象实例，解析不成功则返回null。
	 */
	public static Date paresDate(String source, String pattern) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		// 判断是否为 java.util.Date 的字符串。
		Date date = parseSysDateStr(source);
		if (date != null) {
			return date;
		} else {
			return parseDate(source, pattern);
		}
	}

	/**
	 * 解析java.util.Date toString()方法返回的字符串解析。
	 * 
	 * @param source
	 *            java.util.Date toString() 的字符串。
	 * @return java.util.Date 实例。
	 */
	public static Date parseSysDateStr(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}

		// 判断是否为 java.util.Date 的字符串。
		boolean isDateStr = false;

		for (int weekDay = 0; weekDay < dayOfWeekNames.length; weekDay++) {
			String wd = dayOfWeekNames[weekDay];
			if (source.trim().startsWith(wd)) {
				source = source.trim().replace(wd, "");
				isDateStr = true;
				break;
			}
		}
		if (isDateStr) {
			// java.util.Date 字符串 dow mon dd hh:mm:ss zzz yyyy 规则
			SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getInstance();
			try {
				int month = 0;
				for (; month < monthOfYearNames.length; month++) {
					String mon = monthOfYearNames[month];
					if (source.trim().startsWith(mon)) {
						source = source.trim().replace(mon, "");
						break;
					}
				}
				// dow mon dd hh:mm:ss zzz yyyy
				sdf.applyPattern("dd HH:mm:ss zzz yyyy");
				Date newDate = sdf.parse(source);
				Calendar c = Calendar.getInstance();
				c.setTime(newDate);
				c.set(Calendar.MONTH, month);
				return c.getTime();
			} catch (ParseException e) {
				log.error(e.getMessage());
				return null;
			}
		}
		return null;
	}

	/**
	 * 根据输入格式输出格式来格式化日期字符串
	 */
	public static String formatStr(String str, String inputPattern,
			String outPattern) {
		try {
			Date date = paresDate(str, inputPattern);
			return formatDate(date, outPattern);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 计算两个日期间隔的月份数
	 * 
	 * @param min
	 *            小的日期
	 * @param max
	 *            大的日期
	 * @return
	 */
	public static long countMonth(Date min, Date max) {
		long ft = min.getTime();
		long st = max.getTime();
		long count = st - ft;
		long rs = count / (1000 * 60 * 60) / (24 * 30);
		return rs;
	}

	/**
	 * 延后几天的时间,delay为后延的天数
	 * 
	 * @author duanfen
	 * @param nowdate
	 * @param delay
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getNextDay(int delay) {
		Date date = new Date();// 取时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 需要将date数据转移到Calender对象中操作
		calendar.add(calendar.DATE, delay);// 把日期往后增加n天.正数往后推,负数往前移动
		date = calendar.getTime();
		return date;
	}

	/**
	 * 在最后加上 23:59:59 的时间，用于查询类似于17号到17号的数据
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static String appendMaxTime(String date) {
		return StringUtils.isBlank(date) ? null : (date += " 23:59:59");
	}

	/**
	 * 日期比较
	 * 
	 * @author duanfen
	 * @param DATE1
	 * @param DATE2
	 * @return int
	 */
	public static int compareDate(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
		return 0;
	}

	/**
	 * 定自义格式当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		return formatDate(Calendar.getInstance().getTime(), format);
	}

	/**
	 * 格式化日期
	 * 
	 * @param format
	 *            可以为yyyy-MM-dd，yyyy-MM-dd HH:mm:ss等
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		return DateFormatUtils.format(date, format,
				TimeZone.getTimeZone(DEFAULT_TIME_ZONE),
				Locale.getDefault(Locale.Category.FORMAT));
	}

	/**
	 * 日期转换
	 * 
	 * @param format
	 *            可以为yyyy-MM-dd，yyyy-MM-dd HH:mm:ss等
	 * @return
	 */
	public static Date parseDate(String dateStr, String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);
		java.text.ParsePosition pos = new java.text.ParsePosition(0);
		return formatter.parse(dateStr, pos);
	}

	/**
	 * 日期和时间截取到日期 如dt:yyyy-MM-dd HH:mm:ss 返回yyyy-MM-dd
	 *
	 * @param dt
	 * @return
	 */
	public static Date truncateDate(Date dt) {
		return org.apache.commons.lang3.time.DateUtils.truncate(dt,
				Calendar.DAY_OF_MONTH);
	}

	/**
	 * 计算N天后的日期
	 * 
	 * @param date
	 * @param par
	 * @return
	 */
	public static Date getAfterDay(Date date, int par) {
		return org.apache.commons.lang3.time.DateUtils.truncate(
				org.apache.commons.lang3.time.DateUtils.addDays(date, par),
				Calendar.DAY_OF_MONTH);
	}

	/**
	 * 计算N个月后的日期
	 * 
	 * @param date
	 * @param par
	 * @return
	 */
	public static Date getAfterMonth(Date date, int par) {
		return org.apache.commons.lang3.time.DateUtils.truncate(
				org.apache.commons.lang3.time.DateUtils.addMonths(date, par),
				Calendar.DAY_OF_MONTH);
	}

	/**
	 * 计算N个月后的日期（带顺延），如1月31号加1个月为2月28号，则顺延一天变为3月1号
	 *
	 * @param date
	 * @param par
	 * @return
	 */
	public static Date getAfterMonthNext(Date date, int par) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		int oldDate = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.MONTH, par);
		int newDate = c.get(Calendar.DAY_OF_MONTH);
		// 判断新月天数是否小于旧月天数，若小于则表明出现了28 < 30, 30 < 31之类的，需顺延
		if (newDate < oldDate) {
			c.add(Calendar.DATE, 1); // 加1天
		}
		return org.apache.commons.lang3.time.DateUtils.truncate(c.getTime(),
				Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期 指定分钟后的日期
	 * 
	 * @param monthNum
	 * @return
	 */
	public static Date getDateAfterByMinute(Date date, Integer minute) {
		return org.apache.commons.lang3.time.DateUtils.addMinutes(date, minute);
	}

	/**
	 * 获取某个月的第一天
	 * 
	 * @param dt
	 * @param formatStr
	 * @return
	 */
	public static String getFirstDay(Date dt, String formatStr) {
		return formatDate(org.apache.commons.lang3.time.DateUtils.truncate(dt,
				Calendar.MONTH), formatStr);
	}

	/**
	 * 获取某个月的最后一天 算法如下：1)取本月第一天 2)当月加1 3)日期减一
	 * 
	 * @param dt
	 * @param formatStr
	 * @return
	 */
	public static String getLastDay(Date dt, String formatStr) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(dt);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.add(Calendar.MONTH, 1);
		ca.add(Calendar.DATE, -1);
		return formatDate(ca.getTime(), formatStr);
	}

	/**
	 * 获取某月最后一天 日
	 * 
	 * @param dt
	 * @param formatStr
	 * @return
	 */
	public static int getLastDateDay(Date dt, String formatStr) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(dt);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.add(Calendar.MONTH, 1);
		ca.add(Calendar.DATE, -1);

		return ca.get(Calendar.DATE);
	}

	/**
	 * 
	 * 计算两个日期之间相差几天
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 相差天数
	 * @author zhoudl
	 */
	public static int datePhaseDiffer(Date beginDate, Date endDate) {
		try {
			return (int) ((endDate.getTime() - beginDate.getTime()) / DAY_MILLIS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 计算两个日期之间相差几小时
	 *
	 * @author wangjf
	 * @date 2015年7月2日 下午3:37:23
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int hourPhaseDiffer(Date beginDate, Date endDate) {
		try {
			return (int) ((endDate.getTime() - beginDate.getTime()) / HOUR_MILLIS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 计算两个日期之间相差几分钟
	 *
	 * @author wangjf
	 * @date 2015年7月2日 下午3:37:26
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int minutePhaseDiffer(Date beginDate, Date endDate) {
		try {
			return (int) ((endDate.getTime() - beginDate.getTime()) / MINUTE_MILLIS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 计算两个日期之间相差几秒
	 *
	 * @author wangjf
	 * @date 2015年7月2日 下午3:37:53
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int secondPhaseDiffer(Date beginDate, Date endDate) {
		try {
			return (int) ((endDate.getTime() - beginDate.getTime()) / 1000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断两个日期之间相差多少月
	 * 
	 * @param begindate
	 * @param enddate
	 * @return
	 * @author HuangXiaodong 2014-10-08, 20:28:47
	 * @modified
	 */
	public static int monthPhaseDiffer(Date begindate, Date enddate) {
		try {
			int year1 = getYear(begindate);
			int month1 = getMonth(begindate);
			int day1 = getDay(begindate);
			int year2 = getYear(enddate);
			int month2 = getMonth(enddate);
			int day2 = getDay(enddate);
			int month = (year2 - year1) * 12 + month2 - month1;
			if (day2 - day1 > 0)
				return month + 1;
			else
				return month;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 计算两个日期相差的天、小时、分钟
	 * 
	 * @param start
	 * @param end
	 */
	public static String show(Date start, Date end) {
		long temp = end.getTime() - start.getTime();
		String leavingTime = temp / DAY_MILLIS + "天" + (temp % DAY_MILLIS)
				/ HOUR_MILLIS + "小时" + ((temp % DAY_MILLIS) % HOUR_MILLIS)
				/ MINUTE_MILLIS + "分";
		return leavingTime;
	}

	/**
	 * 
	 * 获取日期中的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取日期中的月份
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getMonth(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(Calendar.MONTH);
	}

	/**
	 * 获取日期中的天
	 *
	 * @param dt
	 * @return
	 */
	public static int getDay(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 比较两个日期 date1 >= date2 返回true，否则返回false
	 * 
	 * @param date1
	 * @param date2
	 * @param day
	 * @return
	 */
	public static boolean compare_date(Date date1, Date date2) {
		boolean flag = false;
		if (date1.getTime() >= date2.getTime()) {
			return true;
		}
		return flag;
	}

	/**
	 * 计算下一个日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	static public Date nextPayDateIncludeToday(Calendar date, String day) {
		Objects.requireNonNull(date);
		int dateDD = date.get(Calendar.DAY_OF_MONTH);
		int dd = Integer.parseInt(day);
		date.set(Calendar.DAY_OF_MONTH, dd);
		if (dateDD > dd) {
			date.add(Calendar.MONTH, 1);
		}
		return date.getTime();
	}

	/**
	 * 获取上一个日期
	 * 
	 * @param day
	 * @return
	 */
	public static Calendar lastPayDateWithPattern(Date date, String day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dd = Integer.parseInt(day);
		int date_dd = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, dd);
		if (date_dd < dd) {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		}
		return cal;
	}

	/**
	 * 页面显示的日期型 显示的样式是yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String showDateString(Date date) {
		if (null == date) {
			throw new RuntimeException("日期不能为空");
		}
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 获取某天起始时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		return truncateDate(date);
	}

	/**
	 * 获取某天结束时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getEndDate(Date date) {
		return parseDate(
				DateUtils.formatDate(date, "yyyy-MM-dd") + " 23:59:59",
				"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取第二天日期
	 *
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date) {
		return getAfterDay(getStartDate(date), 1);
	}

	/**
	 * 获取当月第一天起始时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getMonthStartDate(Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date,
				Calendar.MONTH);
	}

	/**
	 * 将yyyy-MM-dd格式的字符串转为日期格式
	 *
	 * @return Date
	 */
	public static Date parseStandardDate(String dateStr) {
		return DateUtils.parseDate(dateStr, "yyyy-MM-dd");
	}

	/**
	 * 日期Date转换为Timestamp
	 * **/
	public static Timestamp getDateToTimeStamp(Date paramDate) {
		if (paramDate == null) {
			return null;
		}
		return new Timestamp(paramDate.getTime());
	}

	/**
	 * 指定日期之前的某个固定时间
	 * **/
	public static Timestamp getTimeStamp(Date currDate, int num) {
		if (currDate == null) {
			throw new RuntimeException("日期对象不能为空");
		}
		return new Timestamp(getAfterMonth(currDate, -num).getTime());
	}

}
