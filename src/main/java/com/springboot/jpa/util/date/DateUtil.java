package com.springboot.jpa.util.date;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: WangRui
 * Date: 2018/5/20
 * Describe: 日期工具类
 */
public class DateUtil {

    /***
     * 获取某年某月的开始时间
     * @param year 年
     * @param month 月
     * @return 日期
     */
    public static Date getBeginTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /***
     * 获取某年某月的结束时间
     * @param year 年
     * @param month 月
     * @return 日期
     */
    public static Date getEndTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /***
     * 获取某月开始时间
     * @param date 输入日期
     * @return 日期
     */
    public static Date getMonthBiginTime(Date date){
        Calendar calendar = getCalendar(date);
        return getBeginTime(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
    }

    /***
     * 获取某月结束时间
     * @param date 输入日期
     * @return 日期
     */
    public static Date getMonthEndTime(Date date){
        Calendar calendar = getCalendar(date);
        return getEndTime(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
    }


    /***
     * 获取本周一的日期
     * @param date 输入日期
     * @return 日期
     */
    public static Date getMonday(Date date){
        return getDayOfWeek(date,1);
    }

    /***
     * 获取本周末的日期
     * @param date 输入日期
     * @return 日期
     */
    public static Date getSunDay(Date date){
        return getDayOfWeek(date,7);
    }

    private static Date getDayOfWeek(Date date,int add){
        Calendar calendar = getCalendar(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek==0){
            dayOfWeek=7;
        }
        calendar.add(Calendar.DATE,-dayOfWeek+add);
        return calendar.getTime();
    }

    private static Calendar getCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
