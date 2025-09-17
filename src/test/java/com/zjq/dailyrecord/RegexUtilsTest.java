package com.zjq.dailyrecord;

import com.zjq.dailyrecord.security.Md5Encoder;
import com.zjq.dailyrecord.utils.regex.RegexUtils;
import org.junit.Test;

/**
 * @author 共饮一杯无
 * @date 2025/9/16 18:15
 * @description: 正则表达式测试类
 */
public class RegexUtilsTest {

    @Test
    public void isNumericTest() {

        System.out.println("是否是数值："+ RegexUtils.isNumeric("123"));
        System.out.println("是否是数值："+ RegexUtils.isNumeric("123.123"));
        System.out.println("是否是数值："+ RegexUtils.isNumeric("123.123.123"));
        System.out.println("是否是数值："+ RegexUtils.isNumeric("123.12a"));
        System.out.println("是否是数值："+ RegexUtils.isNumeric("a23"));
    }
}
