package com.zohar.array;

import java.util.Collections;
import java.util.List;

public class Matrix {

    /**
     * 将给定的正方形矩阵所有元素顺时针旋转 90°
     * 思路：找规律，发现 (a,b) => (c,d) 之间存在规律：b == c, a + d == len - 1
     * 击败：100%
     *
     * @param matrix 矩阵
     */
    public static void rotateImage(int[][] matrix) {
        if (matrix.length < 2) {
            return;
        }
        for (int i = 0; i < matrix.length / 2; i++) {
            for (int j = i; j < matrix.length - i - 1; j++) {
                /* 每个坐标的旋转都会引发至少 4 次的旋转 */
                int tmp, nowVal = matrix[i][j], nextVal, targetX = j, targetY = matrix.length - i - 1;
                for (int k = 0; k < 4; k++) {
                    nextVal = matrix[targetX][targetY];
                    matrix[targetX][targetY] = nowVal;
                    tmp = targetX;
                    targetX = targetY;
                    targetY = matrix.length - 1 - tmp;
                    nowVal = nextVal;
                }
            }
        }
    }

    /**
     * 将矩阵转化为零矩阵，即零元素所在的行列都转化成零
     * 思路：两个数组解决问题，每有一个零元素，则将该元素的横纵坐标保存在横纵数组中
     * 击败：100%
     *
     * @param matrix 矩阵
     */
    public static void setZeroMatrix(int[][] matrix) {
        if (matrix.length == 0) {
            return;
        }
        boolean[] zeroCol = new boolean[matrix[0].length];
        /* 扫描到 0 元素之后：
         * 0. 将该列零标志置为 true，将该行零标志置为 true
         * 1. 该行所有之前的元素置零
         * 2. 该列所有之前的元素置零
         * 3. 该行剩下待扫描的元素，如果非零则转化为零，如果为零，则对该元素进行 0、1、2 操作
         * 4. 对每一个正在扫描的元素，如果属于 0 列，若不为 0 则转 0，若为 0 则进行 1、2 操作 */
        for (int row = 0; row < matrix.length; row++) {
            boolean zeroRow = false;
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == 0 || zeroRow || zeroCol[col]) {
                    if (matrix[row][col] != 0) {
                        matrix[row][col] = 0;
                        continue;
                    }
                    zeroRow = true;
                    zeroCol[col] = true;
                    for (int k = 0; k < col; k++) {
                        matrix[row][k] = 0;
                    }
                    for (int k = 0; k < row; k++) {
                        matrix[k][col] = 0;
                    }
                }
            }
        }
    }

    /**
     * 对角线遍历，沿 45° 方向遍历矩阵
     * 思路：设置遍历方向标志(isTop), 根据当前坐标 + 遍历方向，即可算出下一个遍历元素的坐标
     * 击败：69.81%
     *
     * @param matrix 矩阵
     * @return 矩阵沿对角线顺序遍历的结果
     */
    public static int[] diagonalTraversal(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }
        boolean isTop = true;
        int[] result = new int[matrix.length * matrix[0].length];
        int index = 0, targetX = 0, targetY = 0;
        while (index < matrix.length * matrix[0].length) {
            result[index++] = matrix[targetX][targetY];
            if (isTop) {
                if (targetX != 0 && targetY != matrix[0].length - 1) {
                    targetX -= 1;
                    targetY += 1;
                } else {
                    isTop = false;
                    if (targetY == matrix[0].length - 1) {
                        targetX += 1;
                    } else {
                        targetY += 1;
                    }
                }
            } else {
                if (targetX != matrix.length - 1 && targetY != 0) {
                    targetX += 1;
                    targetY -= 1;
                } else {
                    isTop = true;
                    if (targetX == matrix.length - 1) {
                        targetY += 1;
                    } else {
                        targetX += 1;
                    }
                }
            }
        }
        return result;
    }
}
