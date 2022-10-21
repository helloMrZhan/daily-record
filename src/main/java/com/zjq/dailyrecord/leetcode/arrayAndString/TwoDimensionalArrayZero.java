package com.zjq.dailyrecord.leetcode.arrayAndString;

import java.util.Arrays;

/**
 * 旋转矩阵
 * 给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。
 * 不占用额外内存空间能否做到？
 * @author zjq
 * @date 2022-09-02
 */
public class TwoDimensionalArrayZero {

    public static void main(String[] args) {
        int[][] intervals = {
                {1,3,5},
                {2,0,6},
                {3,5,7}};
        setZeroes(intervals);
        System.out.println(Arrays.deepToString(intervals));
    }

    public static void setZeroes(int[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        int [] zerosR = new int[r];
        int [] zerosC = new int[c];
        Arrays.fill(zerosR,0);
        Arrays.fill(zerosC,0);
        for (int i=0; i<r; i++){
            for (int j=0; j<c; j++){
                if(matrix[i][j]==0){
                    zerosR[i] = 1;
                    zerosC[j] = 1;
                }
            }
        }
        for (int i=0; i<r; i++){
            for (int j=0;j<c; j++){
                if(zerosR[i]==1 || zerosC[j]==1){
                    matrix[i][j] = 0;
                }
            }
        }
    }

}
