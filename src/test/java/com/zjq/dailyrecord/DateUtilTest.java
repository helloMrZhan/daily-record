package com.zjq.dailyrecord;


import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUnit;
import com.zjq.dailyrecord.dateAndTime.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>基于hutool的日期工具类增强类测试</p>
 *
 * @author zjq
 * @date 2021/04/07
 */
public class DateUtilTest {

    @Test
    public void parseDate() {
        //字符串转Date
        Date dateTime = DateUtil.parseDate("2022-04-06");
        System.out.println(dateTime);
    }

    @Test
    public void date2Str() {
        //Date转字符串不指定format格式，默认yyyy-MM-dd HH:mm:ss
        String dateStr = DateUtil.date2Str(new Date());
        System.out.println(dateStr);
        //Date转字符串指定格式
        String dateStr2 = DateUtil.date2Str("yyyy/MM/dd", new Date());
        System.out.println(dateStr2);
    }

    @Test
    public void parseLocalDate() {
        //字符串转LocalDate
        LocalDate localDate = DateUtil.parseLocalDate("2022-04-06");
        System.out.println(localDate);
    }

    @Test
    public void date2LocalDate() {
        //Date转LocalDate
        LocalDate localDate = DateUtil.date2LocalDate(new Date());
        System.out.println(localDate);
    }

    @Test
    public void localDate2Str() {
        //LocalDate转Str
        String localDateStr = DateUtil.localDate2Str(LocalDate.now());
        System.out.println(localDateStr);
    }

    @Test
    public void dateBetween() {
        String beginDateStr = "2022-02-01 22:33:23";
        Date beginDate = DateUtil.parse(beginDateStr);

        String endDateStr = "2022-03-10 23:33:23";
        Date endDate = DateUtil.parse(endDateStr);
        //相差天数(37)
        long betweenDay = DateUtil.between(beginDate, endDate, DateUnit.DAY);
        System.out.println(betweenDay);
        //格式化时间差(37天1小时)
        String formatBetween = DateUtil.formatBetween(beginDate, endDate, BetweenFormater.Level.HOUR);
        System.out.println(formatBetween);
    }

    @Test
    public void dateStartAndEnd() {
        String dateStr = "2022-04-07 10:33:23";
        Date date = DateUtil.parse(dateStr);

        //一天的开始时间：2022-04-07 00:00:00
        Date beginOfDay = DateUtil.beginOfDay(date);
        System.out.println(beginOfDay);

        //一天的结束时间：2022-04-07 23:59:59
        Date endOfDay = DateUtil.endOfDay(date);
        System.out.println(endOfDay);
    }

    @Test
    public void dateTimeDeal() {

//        DateTime dateTime = DateTime.parse("2017-06-03 06:59:59", org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        DateTime dateTime = DateTime.parse("2017-06-03 07:00:00", org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("当前时间 : " + dateTime.toString("yyyy-MM-dd HH:mm:ss"));

        // 大于等于7点，时间处理成当天的23:59:59
        if (dateTime.getHourOfDay() >= 7) {
            dateTime = new DateTime(dateTime.getYear(),
                    dateTime.getMonthOfYear(), dateTime.getDayOfMonth(),
                    23, 59, 59, 0);
            System.out.println("处理后的时间 : " + dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        } else {
            // 小于七点，时间处理成前一天的23:59:59
            dateTime = new DateTime(dateTime.getYear(),
                    dateTime.getMonthOfYear(),
                    dateTime.getDayOfMonth() - 1, 23, 59, 59, 0);
            System.out.println("处理后的时间 : " + dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        }
    }

}
