package com.zjq.dailyrecord.leetcode.arrayAndString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 找到数组的中间位置
 * @author zjq
 * @date 2022-08-30
 */
public class FindMiddleIndexInArray {

    public static void main(String[] args) {
        List<Integer> listA = new ArrayList<>();
        for (int i = 0; i <10000 ; i++) {
            listA.add(i);
        }
        //int[] objects = listA.toArray();
        int[] objects = new int[]{1,2,3,3,4,2};
        System.out.println(findMiddleIndex(objects));
    }

    public static int findMiddleIndex(int[] nums) {
        int total = Arrays.stream(nums).sum();
        int sum = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (2 * sum + nums[i] == total) {
                return i;
            }
            sum += nums[i];
        }
        return -1;
    }
}
