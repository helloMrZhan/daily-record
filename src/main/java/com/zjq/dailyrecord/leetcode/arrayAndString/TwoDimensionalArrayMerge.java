package com.zjq.dailyrecord.leetcode.arrayAndString;

import java.util.*;

/**
 * 合并区间
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回
 * 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 *
 * @author zjq
 * @date 2022-09-02
 */
public class TwoDimensionalArrayMerge {

    public static void main(String[] args) {
        int[][] intervals = {
                {1,3},
                {2,6},
                {8,10},
                {15,18}};
        //int[][] merge = merge(intervals);
        int[][] merge = mergeVector(intervals);
        System.out.println(Arrays.deepToString(merge));
    }


    /**
     * 合并区间
     * 思路：
     * 1、对二维数组进行排序，按照第一列升序列排列
     * 2、借用临时空间，判断是否需要何合并集合当前值，当前集合是否放入结果集触发点
     * @param intervals 待合并区间数组
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return intervals;
        }
        //1、对二维数组按照第一列升序排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        //2、进行合并数组
        List<int []> list = new ArrayList<>();
        //临时空间，1 判断是否需要合并集合，2 是否放入结果集
        int term[] =intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (term[1]>=intervals[i][0]){
                term[1]=Math.max(term[1],intervals[i][1]);
            }else {
                list.add(term);
                term=intervals[i];
            }
        }
        list.add(term);
        return list.toArray(new int[list.size()][2]);
    }

    /**
     * 合并区间
     * 借用Vector
     * @param intervals
     * @return
     */
    public static int[][] mergeVector(int[][] intervals) {
        if (intervals.length == 0){
            return intervals;
        }
        //按每行的第0列升序排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        //由于我们事先不知道数组大小，所以用Vector类实现动态数组。
        Vector<int[]> integerVector;
        integerVector = new Vector<>();
        //定义一个Int类型数组用于作比较，默认值为第一组二维数组的值。
        int[] ints = intervals[0];
        //循环这个二维数组
        for (int i = 1; i < intervals.length; i++) {
            //如果第一个数组的右端点大于等于下一个数组的左端点，做说明两个数组有所交集。
            if (ints[1] >= intervals[i][0]) {
                //int类型数组的右端点等于两个数组中右端点大的那个值。
                ints[1] = Math.max(ints[1], intervals[i][1]);
            } else {
                //把int类型一维数组ints添加到我们创建的vector类里面。
                integerVector.add(ints);
                //给一维数组重新赋值。
                ints = intervals[i];
            }
        }
        //把最后一个区间添加到Vector里面
        integerVector.add(ints);
        //把vector转换成二维数组返回。
        return integerVector.toArray(new int[integerVector.size()][2]);
    }
}
