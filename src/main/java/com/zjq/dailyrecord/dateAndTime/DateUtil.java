package com.zjq.dailyrecord.dateAndTime;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>基于hutool的日期工具类增强</p>
 *
 * @author zjq
 * @date 2021/04/07
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {

    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 字符串转date类型
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(Object dateStr) {
        if (ObjectUtils.isNull(dateStr)) {
            return null;
        }
        if (dateStr instanceof Double) {
            return org.apache.poi.ss.usermodel.DateUtil.getJavaDate((Double) dateStr);
        }
        return parse(dateStr.toString(), PARSE_PATTERNS);
    }

    /**
     * Date类型转字符串
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2Str(Date date) {
        return date2Str(null, date);
    }

    /**
     * Date类型转字符串
     *
     * @param format
     * @param date
     * @return
     */
    public static String date2Str(String format, Date date) {
        if (ObjectUtils.isNull(date)) {
            return null;
        }
        SimpleDateFormat dateFormat = StrUtil.isNotBlank(format) ?new SimpleDateFormat(format):
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 字符串转LocalTime类型
     *
     * @param dateStr
     * @return
     */
    public static LocalTime parseLocalTime(Object dateStr) {
        if (ObjectUtils.isNull(dateStr)) {
            return null;
        }
        if (dateStr instanceof Double) {
            return date2LocalTime(parseDate(dateStr));
        }
        return LocalTime.parse(dateStr.toString(), TIME_FORMATTER);
    }

    /**
     * Date转LocalTime
     *
     * @param date
     * @return
     */
    public static LocalTime date2LocalTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * LocalTime转Str
     *
     * @param localTime
     * @return HH:mm:ss
     */
    public static String localTime2Str(LocalTime localTime) {
        return localTime2Str(null, localTime);
    }

    /**
     * LocalTime转str
     *
     * @param format    格式
     * @param localTime
     * @return
     */
    public static String localTime2Str(String format, LocalTime localTime) {
        if (null == localTime) {
            return null;
        }
        DateTimeFormatter formatter = StrUtil.isBlank(format) ?
                TIME_FORMATTER : DateTimeFormatter.ofPattern(format);
        return localTime.format(formatter);
    }

    /**
     * 字符串转LocalDate类型
     *
     * @param dateStr
     * @return
     */
    public static LocalDate parseLocalDate(Object dateStr) {
        if (ObjectUtils.isNull(dateStr)) {
            return null;
        }
        if (dateStr instanceof Double) {
            return date2LocalDate(parseDate(dateStr));
        }
        return LocalDate.parse(dateStr.toString(), DATE_FORMATTER);
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDate转Str
     *
     * @param localDate
     * @return
     */
    public static String localDate2Str(LocalDate localDate) {
        return localDate2Str(null, localDate);
    }

    /**
     * LocalDate转Str
     *
     * @param format    格式
     * @param localDate
     * @return
     */
    public static String localDate2Str(String format, LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        DateTimeFormatter formatter = StrUtil.isBlank(format) ?
                DATE_FORMATTER : DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    /**
     * 字符串转LocalDateTime类型
     *
     * @param dateStr
     * @return
     */
    public static LocalDateTime parseLocalDateTime(Object dateStr) {
        if (ObjectUtils.isNull(dateStr)) {
            return null;
        }
        if (dateStr instanceof Double) {
            return date2LocalDateTime(parseDate(dateStr));
        }
        return LocalDateTime.parse(dateStr.toString(), DATETIME_FORMATTER);
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDate转Str
     *
     * @param localDateTime
     * @return
     */
    public static String localDateTime2Str(LocalDateTime localDateTime) {
        return localDateTime2Str(null, localDateTime);
    }

    /**
     * LocalDate转Str
     *
     * @param format
     * @param localDateTime
     * @return
     */
    public static String localDateTime2Str(String format, LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        DateTimeFormatter formatter = StrUtil.isBlank(format) ?
                DATETIME_FORMATTER : DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }
}
