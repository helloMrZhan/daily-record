package com.zjq.dailyrecord.utils.regex;

import java.util.regex.Pattern;

/**
 * @author 共饮一杯无
 * @date 2025/9/16 18:13
 * @description: 正则工具类
 */
public class RegexUtils {

    /**
     * 判断字符串是否是数值
     * @param str
     */
    public static boolean isNumeric(String str) {
        if (Pattern.matches("^-?\\d+(\\.\\d+)?$", str)) {
            return true;
        } else {
            return false;
        }
    }


}
