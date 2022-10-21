package com.zjq.dailyrecord.leetcode.arrayAndString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 对角线遍历
 * 给你一个大小为 m x n 的矩阵 mat ，请以对角线遍历的顺序，用一个数组返回这个矩阵中的所有元素。
 * @author zjq
 * @date 2022-09-02
 */
public class TwoDimensionalArrayFindDiagonalOrder {

    public static void main(String[] args) {
        int[][] intervals = {
                {1,3,5},
                {2,0,6},
                {3,5,7}};
        int[] diagonalOrder = findDiagonalOrder(intervals);
        System.out.println(Arrays.toString(diagonalOrder));
    }

    /**
     * 二维数组的对角线遍历，一定要画图。
     * 有两个特点。
     *
     * 第一个是对角线上的数据（x,y）对应 x+y 的值都是一致的。
     * 当 x+y 为奇数时对角线上的数据正序输出，为偶数时倒叙输出。
     *
     * @param matrix
     * @return
     */
    public static int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length <= 0) {
            return new int[]{};
        }
        //根据每个元素x和y的初始化集合
        List<List<Integer>> axisCount = new ArrayList<>();
        //x和y轴之后最大值
        int maxLength = matrix.length + matrix[0].length - 1;
        for (int i = 0; i < maxLength; i++) {
            //初始化集合
            axisCount.add(new ArrayList<>());
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                //计算每个元素x和y的和
                int a = i + j;
                int value = matrix[i][j];
                //根据x和y轴的和获取list，存放当前value
                axisCount.get(a).add(value);
            }
        }
        List<Integer> dataList = new ArrayList<>();
        for (int i = 0; i < axisCount.size(); i++) {
            List<Integer> singleList = axisCount.get(i);
            //偶数按照插入顺序倒叙排列
            if (i % 2 == 0) {
                Collections.reverse(axisCount.get(i));
            }
            //奇数
            dataList.addAll(singleList);
        }
        int[] arr = new int[dataList.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = dataList.get(i);
        }
        return arr;
    }


}
