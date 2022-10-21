package com.zjq.dailyrecord.leetcode.arrayAndString;

import java.util.Arrays;

/**
 * 旋转矩阵
 * 给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。
 * 不占用额外内存空间能否做到？
 * @author zjq
 * @date 2022-09-02
 */
public class TwoDimensionalArrayRotate {

    public static void main(String[] args) {
        int[][] intervals = {
                {1,3,5},
                {2,4,6},
                {3,5,7}};
        rotate2(intervals);
        System.out.println(Arrays.deepToString(intervals));
    }

    /**
     * 给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。
     * <p>
     * 不占用额外内存空间能否做到？
     *
     * @param matrix int类型二维数组
     */
    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = matrix[i][j] ^ matrix[n - i - 1][j];
                matrix[n - i - 1][j] = matrix[i][j] ^ matrix[n - i - 1][j];
                matrix[i][j] = matrix[i][j] ^ matrix[n - i - 1][j];
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                matrix[i][j] = matrix[i][j] ^ matrix[j][i];
                matrix[j][i] = matrix[i][j] ^ matrix[j][i];
                matrix[i][j] = matrix[i][j] ^ matrix[j][i];
            }
        }
    }

    public static void rotate2(int[][] matrix) {
        for (int i = 0; i < matrix.length/2; i++) {
            for (int j = i; j < matrix.length - (i + 1); j++) {
                // 相邻交换一次
                matrix[i][j] = matrix[i][j]^matrix[j][matrix.length-1-i];
                matrix[j][matrix.length-1-i] = matrix[i][j]^matrix[j][matrix.length-1-i];
                matrix[i][j] = matrix[i][j]^matrix[j][matrix.length-1-i];
                // 对角交换一次
                matrix[i][j] = matrix[i][j]^matrix[matrix.length-1-i][matrix.length-1-j];
                matrix[matrix.length-1-i][matrix.length-1-j] = matrix[i][j]^matrix[matrix.length-1-i][matrix.length-1-j];
                matrix[i][j] = matrix[i][j]^matrix[matrix.length-1-i][matrix.length-1-j];
                // 相邻交换一次
                matrix[i][j] = matrix[i][j]^matrix[matrix.length-1-j][i];
                matrix[matrix.length-1-j][i] = matrix[i][j]^matrix[matrix.length-1-j][i];
                matrix[i][j] = matrix[i][j]^matrix[matrix.length-1-j][i];
            }
        }
    }

}
