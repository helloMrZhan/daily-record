package com.zjq.dailyrecord.utils.bigdecimal;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * BigDecimal工具类测试
 * @author zjq
 * @date 2022-06-09
 */
public class BigDecimalTest {

    @Test
    public void gtTest() {
        BigDecimal big1 = new BigDecimal(5000);
        BigDecimal big2 = new BigDecimal(1000);
        System.out.println(BigDecimalUtil.gt(big1, big2));
    }

    @Test
    public void ltTest() {
        BigDecimal big1 = new BigDecimal(500);
        BigDecimal big2 = new BigDecimal(1000);
        System.out.println(BigDecimalUtil.lt(big1, big2));
    }

    @Test
    public void geTest() {
        BigDecimal big1 = new BigDecimal(5000);
        BigDecimal big2 = new BigDecimal(1000);
        System.out.println(BigDecimalUtil.ge(big1, big2));
    }

    @Test
    public void leTest() {
        BigDecimal big1 = new BigDecimal(5000);
        BigDecimal big2 = new BigDecimal(1000);
        System.out.println(BigDecimalUtil.le(big1, big2));
    }

    @Test
    public void eqTest() {
        BigDecimal big1 = new BigDecimal(1000);
        BigDecimal big2 = new BigDecimal(1000);
        System.out.println(BigDecimalUtil.eq(big1, big2));
    }

    @Test
    public void intValueTest() {
        BigDecimal big1 = new BigDecimal(-100.39);
        System.out.println(BigDecimalUtil.intValue(big1, BigDecimal.ROUND_UP));
    }

    @Test
    public void longValueTest() {
        BigDecimal big1 = new BigDecimal(100.59);
        System.out.println(BigDecimalUtil.longValue(big1, BigDecimal.ROUND_CEILING));
    }

    @Test
    public void floatValueTest() {
        BigDecimal big1 = new BigDecimal(100.454845);
        System.out.println(BigDecimalUtil.floatValue(big1));
    }

    @Test
    public void toPlainStringTest() {
        BigDecimal big1 = new BigDecimal(100.457845);
        System.out.println(BigDecimalUtil.toPlainString(big1,2,BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void halfUpValueTest() {
        BigDecimal big1 = new BigDecimal(100.455145);
        System.out.println(BigDecimalUtil.halfUpValue(big1,3));
    }

    @Test
    public void roundingModeValueTest() {
        BigDecimal big1 = new BigDecimal(100.455);
        System.out.println(BigDecimalUtil.roundingModeValue(big1,2,BigDecimal.ROUND_HALF_DOWN));
    }
}
