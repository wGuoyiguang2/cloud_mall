package com.hqtc.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * description:日期工具类
 * Created by laiqingchuang on 18-6-28 .
 */
public class DateUtil {
	
	private static SimpleDateFormat getSimpleDateFormat(String format){
		return new SimpleDateFormat(format);
	}
	
	/**
	 * 根据类型，返回当前日期的格式化字符串
	 * @param formatStr
	 */
	public static String getFormatDate(String formatStr){
		Date date = new Date();
		SimpleDateFormat dateformat = getSimpleDateFormat(formatStr);
		return dateformat.format(date);
	}
	
	/**
	 * 根据类型，返回参数日期的格式化字符串
	 * @param date
	 * @param formatStr
	 */
	private static String getFormatDate(Date date, String formatStr) {
		SimpleDateFormat dateformat = getSimpleDateFormat(formatStr);
		return dateformat.format(date);
	}
	
	/**
	 * 格式化字符串date 为Date类型 格式 年-月-日
	 */
	public static Date getDateFormatStr(String date) throws ParseException{
		SimpleDateFormat dateformat = getSimpleDateFormat("yyyy-MM-dd");
		return dateformat.parse(date);
	}

	/**
	 * 格式化字符串date 为Date类型 格式 时:分
	 */
	public static Date getDateFormatStrHHmm(String date) throws ParseException{
		SimpleDateFormat dateformat = getSimpleDateFormat("HH:mm");
		return dateformat.parse(date);
	}
	
	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：年月日时分秒
	 */
	public static String getSimpleDateFromatInt(){
		return getFormatDate("yyyyMMddHHmmss");
	}

	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：年-月-日 时:分:秒
	 */
	public static String getSimpleDateFormat(){
		return getFormatDate("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：年-月-日
	 */
	public static String getSimpleDate(){
		return getFormatDate("yyyy-MM-dd");
	}
	
	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：年-月
	 */
	public static String getSimpleDateYM(){
		return getFormatDate("yyyy-MM");
	}
	
	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：年-月-日
	 */
	public static String getSimpleDate(Date data){
		return getFormatDate(data, "yyyy-MM-dd");
	}

	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：时:分
	 */
	public static String getSimpleDateHHmm(Date data){
		return getFormatDate(data, "HH:mm");
	}
	
	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：年-月-日 时:分:秒
	 */
	public static String getSimpleDateFormat(Date date){
		return getFormatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：年-月-日 时:分:秒.毫秒
	 */
	public static String getSimpleDateFormatSSS(Date date){
		return getFormatDate(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：时分秒毫秒
	 */
	public static String getSimpleDateFormatIntSSS(Date date){
		return getFormatDate(date, "HHmmssSSS");
	}
	
	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：月-日
	 */
	public static String getSimpleMD(Date date){
		return getFormatDate(date, "MM-dd");
	}
	
	/**
	 * 格式化系统当前时间 
	 * 格式化后的日期字符串 格式：年
	 */
	public static String getSimpleDateFormatYear(){
		return getFormatDate("yyyy");
	}
	
	/**
	 * 格式化几天前的日期
	 * 格式化后的日期字符串 格式：年-月-日 时:分:秒
	 */
	public static String getBeforeDateFormat(int day){
		long longBeforeDate = System.currentTimeMillis() - (1000 * 60 * 60 * 24 * day);
		Date date = new Date(longBeforeDate);
		return getSimpleDateFormat(date);
	}
	
	/**
	 * 格式化几天前的日期
	 * 格式化后的日期字符串 格式：年-月-日
	 * @param day
	 */
	public static String getBeforeDateYMD(int day){
		long longBeforeDate = System.currentTimeMillis() - (1000 * 60 * 60 * 24 * day);
		Date date = new Date(longBeforeDate);
		return getFormatDate(date, "yyyy-MM-dd");
	}
	
	/**
	 * 与date时间相差day天的时间 格式  年-月-日
	 * date 当前时间
	 * day 负数 向后推几天
	 * day 整数 向前推几天
	 */
	public static String getSimpleDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return getSimpleDate(cal.getTime());
	}
	
	/**
	 * 与date时间相差day天的时间 格式 月-日
	 * date 当前时间
	 * day 负数 向后推几天
	 * day 整数 向前推几天
	 */
	public static String getSimpleMDDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return getSimpleMD(cal.getTime());
	}
	
	/**
	 * 获取连个时间 相差几天
	 */
	public static int getDay(Date startTime, Date endTime){
		return (int)((endTime.getTime() - startTime.getTime()) / 24 / 60 / 60 / 1000);
	}
	
	/**
	 * 格式化字符串date 为Date类型 格式 年-月-日 时：分：秒
	 */
	public static Date getDateFormatStrHms(String date) throws ParseException{
		SimpleDateFormat dateformat = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat.parse(date);
	}
	
	/**
	 * 格式化字符串date 为Date类型 格式 年-月-日 时：分
	 */
	public static Date getDateFormatStrHm(String date) throws ParseException{
		SimpleDateFormat dateformat = getSimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateformat.parse(date);
	}
	
	/**
	 * 时间加秒
	 * @param date    时间
	 * @param time    相加时间
	 */
	public static Date getAddDate(Date date,int time){
		long longTime = date.getTime() + time;
		Date dateTime = new Date(longTime);
		return dateTime;
	}
	
	/**
	 * 倒计时函数
	 * @param i
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getJianDate(Long i,Date date1,Date date2){
		long longTime1 = date1.getTime() ;
		long longTime2 = date2.getTime() ;
		return i-(longTime1-longTime2);
	}
	
	/**
	 * 获取时间 周几 数据
	 */
	public static String getWeekOfData(Date date){
		return getFormatDate(date, "EEEE");
	}
	
	/**
	 * 时间 加减 月份
	 */
	public static Date getBeforeDateMonth(Date date, int month){
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) + month);
		return cl.getTime();
	}
	
	/**
	 * 系统时间 加减 月份
	 */
	public static Date getBeforeDateMonth(int month){
		return getBeforeDateMonth(new Date(), month);
	}
	
	/**
	 * 根据时间字符串获取oracle插入时间方式
	 * @param dateFormat	yyyy-MM-dd HH:mm:ss
	 */
	public static String getOracleInsertDate(String dateFormat){
		return String.format("TO_DATE('%s', 'YYYY-MM-DD HH24:MI:SS')", dateFormat);
	}
	
	/**
	 * 根据时间字符串获取oracle插入时间方式
	 */
	public static String getOracleInsertDate(Date date){
		return String.format("TO_DATE('%s', 'YYYY-MM-DD HH24:MI:SS')", getSimpleDateFormat(date));
	}
}