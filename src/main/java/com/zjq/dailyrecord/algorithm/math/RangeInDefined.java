package com.zjq.dailyrecord.algorithm.math;

/**
 * @author zjq
 * @date 2022/11/23 20:07
 * <p>title:判断一个数值是否在某两个数值之间</p>
 * <p>description:</p>
 */
public class RangeInDefined {

    public static boolean rangeInDefined(int current, int min, int max) {
        return Math.max(min, current) == Math.min(current, max);
    }

    public static void main(String[] args) {
        int current = 50;
        if (rangeInDefined(current, 1, 100)) {
            System.out.println(current + "在1-100之间");
        }else {
            System.out.println("不在");
        }
    }

}
