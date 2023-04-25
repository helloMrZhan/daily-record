package com.zjq.dailyrecord.algorithm.list;

import java.util.Arrays;

/**
 * @Author : zjq
 * @CreateTime : 2020/2/01
 * @Description : 数组排序
 **/
public class ArraySortUtil {


    //封装好的方法排序
    public static void ArraysSort(int[] nums) {
        Arrays.sort(nums);

        for (int a : nums) {

            System.out.print(a);
        }
    }

    //冒泡排序
    public static void BubbleSort(int[] nums) {
        int i, j, k;
        int n = nums.length;
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < n - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    k = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = k;
                }
            }

        }

        for (int a : nums) {

            System.out.print(a);
        }

    }

    //选择排序
    public static void SelectSort(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int tem = i;
            //将数组中从i开始的最小的元素所在位置的索引赋值给tem
            for (int j = i; j < n; j++) {
                if (nums[j] < nums[tem]) {
                    tem = j;
                }
            }
            //上面获取了数组中从i开始的最小值的位置索引为tem，利用该索引将第i位上的元素与其进行交换
            int temp1 = nums[i];
            nums[i] = nums[tem];
            nums[tem] = temp1;
        }


        for (int a : nums) {

            System.out.print(a);
        }

    }

    //反转排序
    public static void ReversalSort(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n / 2; i++) {
            int tp = nums[i];
            nums[i] = nums[nums.length - i - 1];
            nums[nums.length - i - 1] = tp;
        }

        for (int a : nums) {

            System.out.print(a);
        }

    }

    //插入排序
    public static void InsertSort(int[] nums) {
        int n = nums.length;

        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (nums[j - 1] > nums[j]) {//大的放后面
                    int tmp = nums[j - 1];
                    nums[j - 1] = nums[j];
                    nums[j] = tmp;
                }
            }
        }

        for (int a : nums) {

            System.out.print(a);
        }

    }

    public static void main(String[] args) {

        int[] nums = new int[]{3, 5, 1, 7, 9};

        ArraysSort(nums);
        System.out.println("\n");
        BubbleSort(nums);
        System.out.println("\n");
        SelectSort(nums);
        System.out.println("\n");
        ReversalSort(nums);
        System.out.println("\n");
        InsertSort(nums);
    }


}
