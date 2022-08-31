package com.zjq.dailyrecord.leetcode.arrayAndString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 搜索插入位置
 * @link
 * @author zjq
 * @date 2022-08-30
 */
public class SearchInsertInArray {

    public static void main(String[] args) {
        int[] objects = new int[]{1,2,3,3,4,2};
        System.out.println(searchInsertPlus(objects,6));
    }

    /**
     * 暴力查找
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {
        for(int i=0;i<nums.length;i++){
            if(nums[i]>=target) {
                return i;
            }
        }
        return nums.length;
    }

    /**
     * 二分法
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsertPlus(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = nums[mid];
            if (midValue > target) {
                right = mid - 1;
            }else if (midValue < target) {
                left = mid + 1;
            }else {
                //如果找到就返回
                return mid;
            }
        }
        //如果没找到就返回应该插入的位置
        return left;
    }

}
