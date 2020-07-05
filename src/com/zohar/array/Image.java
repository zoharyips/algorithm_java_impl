package com.zohar.array;

import java.util.Collections;
import java.util.List;

public class Image {

    /**
     * 返回杨辉三角第 n + 1 行的内容
     *
     * @param rowIndex 行号
     * @return 第 n + 1 行内容
     */
    public static List<Integer> getYangHuiTriangleRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex > 33) {
            return Collections.emptyList();
        }
        Integer[] row = new Integer[rowIndex + 1];
        java.util.Arrays.fill(row, 1);
        if (rowIndex > 1) {
            int lastNum;
            /* 行数 */
            for (int i = 1; i < rowIndex; i++) {
                for (int j = 1; j < rowIndex - i + 1; j++) {
                    lastNum = row[j - 1];
                    row[j] = lastNum + row[j];
                }
            }
        }
        return java.util.Arrays.asList(row);
    }

    /**
     * 直方图拿来装水，求哪两条边装的水最多，返回最多的那个容量
     * 击败：75%
     *
     * @param height 各高度
     * @return 最大盛水量
     */
    public static int maxWaterArea(int[] height) {
        int LIdx = 0, RIdx = height.length - 1, res = 0;
        while (LIdx < RIdx) {
            res = Math.max(res, Math.min(height[RIdx], height[LIdx]) * (RIdx - LIdx));
            if (height[RIdx] > height[LIdx]) {
                LIdx++;
            } else {
                RIdx--;
            }
        }
        return res;
    }
}
