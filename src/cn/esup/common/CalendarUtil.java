package cn.esup.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * DateUtil
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
public class CalendarUtil {

	public static String SHORT_PATTERN = "yyyy-MM-dd";
	public static String LONG_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * String to Calendar
	 * 
	 * @param pattern
	 *            date style string formatter
	 * @param dateStr
	 *            date
	 * @return
	 * @throws ParseException
	 */
	public static Calendar string2Calendar(String pattern, String dateStr) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(simpleDateFormat.parse(dateStr));
		return calendar;
	}

	/**
	 * Calendar to String
	 * 
	 * @param pattern
	 * @param calendar
	 * @return
	 */
	public static String calendar2String(String pattern, Calendar calendar) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String tmp = simpleDateFormat.format(calendar.getTime());
		return tmp;
	}

	/**
	 * test
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println(CalendarUtil.calendar2String(LONG_PATTERN, Calendar.getInstance()));
		//System.out.println(CalendarUtil.string2Calendar(LONG_PATTERN, "2011-10-22 13:41:20"));
	}
}
