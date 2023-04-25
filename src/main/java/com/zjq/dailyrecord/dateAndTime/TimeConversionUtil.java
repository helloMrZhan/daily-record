package com.zjq.dailyrecord.dateAndTime;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zjq
 * @CreateTime: 2018-09-11
 * @Description:
 *           2018-12-04 1.使用DateTimeFormatter替换了部分SimpleDateFormat, 因为线程安全问题。
 *                      2.后期这个工具类应该需要优化，因为有Instant,这个专门获取时间戳的类
 *           2018-12-12 将SimpleDateFormat相关的方法全部去除替换
 */
public class TimeConversionUtil {


    /**
     * 传入long时间戳 ，转换成格式化的String类型时间
     * @param time
     * @return
     */
    public  String TimeOfLongToStr(long time) {
        Assert.notNull(time, "time is null");
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }


    /**
     * 传入String类型格式化时间，转换成Long类型的时间戳
     * @param strTime
     * @return
     */
    public  long TimeOfStrToLong(String strTime){
        Assert.notNull(strTime, "time is null");
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(strTime, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

    }


    /**
     * 通过LocalDateTime获取当前格式化时间
     * @return
     */
    public  static String getTimeStrNow(){
        //DateTimeFormatter替换了SimpleDateFormat, 因为线程安全问题。
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 通过LocalDateTime获取当前格式化时间
     * @return
     */
    public  static  String getTimeStrNow1(){
        //DateTimeFormatter替换了SimpleDateFormat, 因为线程安全问题。
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    /**
     * 获取当前时间戳方法一
     * @return
     */
    public long getTimeLongOfClock(){

        return Clock.systemDefaultZone().millis();
    }

    /**
     * 获取当前时间戳方法二
     * @return
     */
    public long getTimeLongOfCalendar(){
        Calendar calendar = Calendar.getInstance();

        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前时间戳方法三
     * @return
     */
    public long getTimeLongOfSystem(){

        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间戳方法四
     * @return
     */
    public long getTimeLongOfDate(){

        Date date = new Date();

        return date.getTime();
    }

    /**
     * 获取当前时间戳五
     * @return
     */
    public long getTimeLongOfInstant(){

        Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
     //   System.out.println("秒数:"+now.getEpochSecond());
     //   System.out.println("毫秒数:"+now.toEpochMilli());
        return now.toEpochMilli();
    }

    /**
     * 获取两天之间相差多少天
     * @param timeStr
     * @return
     */
    public static String getTransTime(String timeStr) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(timeStr, df);
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String transTime = f2.format(date);

        return transTime;
    }


//    工具类调试
    public static void main(String[] args) {
        TimeConversionUtil timeConversionUtil=new TimeConversionUtil();
        System.out.println(timeConversionUtil.getTimeLongOfCalendar());
        System.out.println(timeConversionUtil.getTimeLongOfDate());
        System.out.println(timeConversionUtil.getTimeLongOfInstant());
        System.out.println(timeConversionUtil.TimeOfLongToStr(timeConversionUtil.getTimeLongOfDate()));
        System.out.println(TimeConversionUtil.getTimeStrNow());

    }

}

