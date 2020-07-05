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

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sameInTwoSortedArrays(new int[]{}, new int[]{4, 8, 128, 256})));
        System.out.println(Arrays.toString(sameInTwoSortedArrays(new int[]{1, 2, 4, 8, 16, 32, 64, 128}, new int[]{4})));
        System.out.println(Arrays.toString(sameInTwoSortedArrays(new int[]{1, 2, 4, 8, 16, 32, 64, 128}, new int[]{})));
        System.out.println(Arrays.toString(sameInTwoSortedArrays(new int[]{1, 2, 4, 8, 16, 32, 64, 128}, new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256})));
    }
}
