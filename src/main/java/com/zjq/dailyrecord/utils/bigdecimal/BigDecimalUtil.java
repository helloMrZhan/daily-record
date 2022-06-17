package com.zjq.dailyrecord.utils.bigdecimal;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类
 * @author zjq
 * @date 2022-06-09
 */
@Getter
@Setter
@Slf4j
public class BigDecimalUtil {



    private BigDecimal bigDecimal;

    /**
     * 默认精度为0
     */
    private final static int scala = 4;
    /**
     * 精度为2
     */
    private final static int SCALA_TWO = 2;
    /**
     * 精度为0（取整）
     */
    private final static int SCALA_ZERO = 0;

    /**
     * 默认舍入模式为 BigDecimal.ROUND_CEILING
     */
    private int roundingMode;

    /**
     * 屏蔽默认构造函数
     */
    private BigDecimalUtil() {
    }


    /**
     * 比较num1是否大于num2
     * @param num1
     * @param num2
     * @return
     */
    public static boolean gt(@NotNull BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > 0;
    }

    /**
     * 比较num1是否小于num2
     * @param num1
     * @param num2
     * @return
     */
    public static boolean lt(@NotNull BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) < 0;
    }

    /**
     * 比较num1是否大于等于num2
     * @param num1
     * @param num2
     * @return
     */
    public static boolean ge(@NotNull BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) >= 0;
    }

    /**
     * 比较num1是否小于等于num2
     * @param num1
     * @param num2
     * @return
     */
    public static boolean le(@NotNull BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) <= 0;
    }

    /**
     * 比较num1是否等于num2
     * @param num1
     * @param num2
     * @return
     */
    public static boolean eq(@NotNull BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 0;
    }

    /**
     * 取整返回int 类型
     * @param num1
     * @param roundingMode
     * @return
     */
    public static int intValue(@NotNull BigDecimal num1,int roundingMode) {
        return num1.setScale(SCALA_ZERO, roundingMode).intValue();
    }

    /**
     * 取整返回long 类型
     * @param num1
     * @param roundingMode
     * @return
     */
    public static long longValue(@NotNull BigDecimal num1,int roundingMode) {
        return num1.setScale(scala, roundingMode).longValue();
    }


    /**
     * 四舍五入保留两位小数返回float类型
     * @param num1
     * @return
     */
    public static float floatValue(@NotNull BigDecimal num1) {
        return num1.setScale(SCALA_TWO, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 四舍五入保留两位小数返回double类型
     * @param num1
     * @return
     */
    public static double doubleValue(@NotNull BigDecimal num1) {
        return num1.setScale(SCALA_TWO, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入保留几位小数
     * @param scala 保留几位
     * @param num1 对应数值
     * @return
     */
    public static float halfUpValue(@NotNull BigDecimal num1,int scala) {
        return num1.setScale(scala, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 指定取舍规则，保留几位小数
     * @param scala 保留几位
     * @param num1 对应数值
     * @param roundingMode 取舍规则
     * @return
     */
    public static BigDecimal roundingModeValue(@NotNull BigDecimal num1,int scala,int roundingMode) {
        /**
         * setScale(1,BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3
         * setScale(1,BigDecimal.ROUND_UP)进位处理，2.35变成2.4
         * setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
         * setScale(1,BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍
         */
        return num1.setScale(scala, roundingMode);
    }

    /**
     * 四舍五入保留几位小数返回字符串
     * @param tScala 保留几位
     * @param num1 对应数值
     * @param tRoundingMode 舍入类型
     * @return
     */
    public static String toPlainString(@NotNull BigDecimal num1, int tScala, int tRoundingMode) {
        return num1.setScale(tScala, tRoundingMode).toPlainString();
    }
}
