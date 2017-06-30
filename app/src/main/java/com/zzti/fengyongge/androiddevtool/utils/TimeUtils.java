package com.zzti.fengyongge.androiddevtool.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author fengyongge
 * @Description  时间处理
 */

public class TimeUtils {


	/**
	 * 获取时间戳 毫秒
	 * @return
	 */
	private static String getTime() {
		return System.currentTimeMillis() + "";
	}

	/**
	 * 时间相差多少
	 * @param nowtime
	 * @param endtime
	 * @return
	 */
	public static long timeDifference(String nowtime, String endtime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long diff = 0;
		try {
			Date dstart = format.parse(nowtime);
			Date dend = format.parse(endtime);
			diff = dend.getTime() - dstart.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}


	/**
	 * 获取与当前比较的时间
	 * @return
	 */
	public static String getBetween(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date m3 = c.getTime();
		String year = format.format(m3);
		return year;
	}

	/**
	 * 时间戳转字符串
	 * @param time
	 * @return
	 */
	public static String getTime(long time){
		String sb;
		Date dat=new Date(time);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dat);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb=format.format(gc.getTime());
		return sb;
	}


	/**
	 * 字符串转为时间戳
	 * @param user_time
	 * @return
	 */
	public static String getTime(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		try {
			d = sdf.parse(user_time);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);
		}catch (ParseException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		return re_time;
	}


	/**
	 * 处理时间
	 * @param time  朋友圈
	 * @return
	 */
	public static String handTime(String time) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (StringUtils.isEmpty(time)) {
			return "";
		}
		try {

			Date date = format.parse(time);
			long tm = System.currentTimeMillis();// 当前时间戳
			long tm2 = date.getTime();// 发表动态的时间戳
			long d = (tm - tm2) / 1000;// 时间差距 单位秒
			if ((d / (60 * 60 * 24)) > 1) {
				return time.substring(0, 10);
			} else if ((d / (60 * 60 * 24)) > 0) {
				return "昨天";
			} else if ((d / (60 * 60)) > 0) {
				return d / (60 * 60) + "小时前";
			} else if ((d / 60) > 0) {
				return d / 60 + "分钟前";
			} else {
				// return d + "秒前";
				return "刚刚";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据时间字符串获取时间
	 * @param timeString
	 */
	public static String getDate(String timeString){
		String firstDay="";
		String endDay="";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date2;
			date2 = format.parse(timeString);
			Calendar c = Calendar.getInstance();
			c.setTime(date2);
			// 获取前月的第一天
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			firstDay = format.format(c.getTime());
			// 获取前月的最后一天
			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, 0);
			endDay = format.format(c.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return firstDay+":"+endDay;
	}


}