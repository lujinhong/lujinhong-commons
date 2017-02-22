package com.lujinhong.commons.java.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    //计算某个日期n天前后的日期，并指定打印格式。正值表示向后计算，负数表示向前计算。
    public static String getBeforeOrAfterDay(String startDay, int dayCount) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar = Calendar.getInstance();
        Date date = sf.parse(startDay);
        calendar.setTime(date);
        calendar.add(calendar.DATE, dayCount);

        Date day = calendar.getTime();
        String returnDay = sf.format(day);
        return returnDay;
    }

    //计算某个日期过去N天所有日期的集合
    public static String[] getLastDays(String day, int dayCount) throws ParseException {
        String[] days = new String[dayCount];
        for (int i = 0; i < dayCount; i++) {
            days[i] = getBeforeOrAfterDay(day, -i);
        }
        return days;
    }

    //判断某个日期是星期几
    public static int getWeekDay(String day) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        Date date = sf.parse(day);
        calendar.setTime(date);
        //注意1：星期天，2：星期一.....7：星期六
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //判断某个日期是否工作日，星期六及星期天为非工作日，其余为工作日。返回值1：工作日，0：休息日。
    public static String getWorkDay(String day){
        int weekDay = 0;
        try {
            weekDay = getWeekDay(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY) {
            return "0";
        } else {
            return "1";
        }
    }

    //打印当前时间
    public static void  printCurrentTime(){
        System.out.println(Calendar.getInstance().getTime());//Thu Feb 16 18:55:56 CST 2017

    }


    //判断某个时间是否工作时间。10:00~17:00为工作时间，21:00~06:00为休息时间。输入值为小时。返回值1：工作时间，0：休息时间，-1：其它时间。
    public static String getWorkTime(String time){
        int hour = Integer.parseInt(time);
        if(hour>=10 && hour<=16){
            return "1";
        }else if((hour>=0 && hour<=5) || (hour>=21 && hour<=23)){
            return "0";
        }else {
            return "-1";
        }
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(getBeforeOrAfterDay("20170201", -1));
        System.out.println(getWeekDay("20170216"));
        System.out.println(Arrays.toString(getLastDays("20170216", 30)));
        printCurrentTime();
    }

}

