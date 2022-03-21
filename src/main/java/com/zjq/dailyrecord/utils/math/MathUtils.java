package com.zjq.dailyrecord.utils.math;

/**
 * Math常用的数学运算(包括取整、取绝对值、保留几位小数等)
 * @author zjq
 * @date 2022/3/21
 */
public class MathUtils {

    public static void main(String[] args) {
        //返回两个数的最大值（支持int long float double）
        System.out.println(Math.max(1,2));
        //返回两个数的最小值（支持int long float double）
        System.out.println(Math.min(1,2));
        //返回一个数的绝对值（支持int long float double）
        System.out.println(Math.abs(-15.6));
        //返回一个数四舍五入后取整（支持float double）注意， float型取整后是int型，而double取整后是long型。
        System.out.println(Math.round(15.6));
        //返回向下取整的值（支持 double）
        System.out.println(Math.floor(15.6));
        //返回大于等于0小于1的随机数
        System.out.println(Math.random());
        //返回2的3次方
        System.out.println(Math.pow(2,3));
        //保留n位小数：策略是先乘以10的n次方，取整后转化为浮点数，再除以10的n次方
        System.out.println(SplitAndRound(2.3659,2));
    }

    /**
     * 为num保留n位小数
     * @param num
     * @param n
     * @return
     */
    public static double SplitAndRound(double num, int n) {
        num = num * Math.pow(10, n);
        return (Math.round(num)) / (Math.pow(10, n));
    }
}
