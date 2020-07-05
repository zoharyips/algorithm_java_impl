package com.zohar.array;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiArrays {
    /**
     * 获取两个已排序数组中的相同元素
     * 注：是元素，各数组中可以存在值相同的元素，每个元素只匹配一次
     * 思路：采用双指针的方式进行匹配
     *
     * @param nums1 数组一
     * @param nums2 数组二
     * @return 二数组中的相同元素
     */
    public static int[] sameInTwoSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0 || nums2.length == 0) return new int[0];
        ArrayList<Integer> tmpRes = new ArrayList<>();
        for (int i = 0, j = 0; i < nums1.length && j < nums2.length; i++, j++) {
            if (nums1[i] == nums2[j]) {
                tmpRes.add(nums1[i]);
            } else if (nums1[i] > nums2[j]) {
                i--;
            } else {
                j--;
            }
        }
        int[] res = new int[tmpRes.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = tmpRes.get(i);
        }
        return res;
    }

    /**
     * 寻找两个有序数组的中位数
     *
     * @param nums1 数组一
     * @param nums2 数组二
     * @return 二者的中位数
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        /* 当其中一个为空时，求另一个的中位数：O(1) */
        if (nums1.length == 0 || nums2.length == 0) {
            int[] availableNums = nums1.length == 0 ? nums2 : nums1;
            return SingleArray.findMedianSortedArray(availableNums);
        }
        /* 当二者不相交时，求整体中位数：O(1) */
        if (nums1[0] > nums2[nums2.length - 1] || nums2[0] > nums1[nums1.length - 1]) {
            int[] LNums = nums1[0] > nums2[nums2.length - 1] ? nums2 : nums1;
            int[] RNums = nums1[0] > nums2[nums2.length - 1] ? nums1 : nums2;
            int len = LNums.length + RNums.length;
            int targetIdx;
            if ((len & 1) == 0) {
                targetIdx = len / 2 - 1;
                if (targetIdx >= LNums.length) {            // 左数组短
                    return (double) (RNums[targetIdx - LNums.length] + RNums[targetIdx - LNums.length + 1]) / 2;
                } else if (targetIdx + 1 == LNums.length){  // 长度相等
                    return (double) (LNums[targetIdx] + RNums[0]) / 2;
                } else {                                    // 左数组长
                    return (double) (LNums[targetIdx] + LNums[targetIdx + 1]) / 2;
                }
            } else {
                targetIdx = (1 + len) / 2 - 1;
                return targetIdx >= LNums.length ? (double) RNums[targetIdx - LNums.length] : (double) LNums[targetIdx];
            }
        }
        /* 当二者相包含时 */

        return 0.0;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sameInTwoSortedArrays(new int[]{}, new int[]{4, 8, 128, 256})));
        System.out.println(Arrays.toString(sameInTwoSortedArrays(new int[]{1, 2, 4, 8, 16, 32, 64, 128}, new int[]{4})));
        System.out.println(Arrays.toString(sameInTwoSortedArrays(new int[]{1, 2, 4, 8, 16, 32, 64, 128}, new int[]{})));
        System.out.println(Arrays.toString(sameInTwoSortedArrays(new int[]{1, 2, 4, 8, 16, 32, 64, 128}, new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256})));
    }
}
