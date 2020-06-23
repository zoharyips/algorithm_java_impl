package com.zohar;

public class Array {

    /**
     * 寻找数组的中心索引
     * 中心索引：中心索引处元素左右两边求和相等
     * 思路：索引处左边求和的两倍加索引元素等于数组总和
     * 击败：100%
     *
     * @param nums 待处理数组
     * @return 中心索引
     */
    public static int pivotIndexOf(int[] nums) {
        if (nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return 0;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int leftSum = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (sum == leftSum * 2 + nums[i]) {
                return i;
            }
            leftSum += nums[i];
        }
        return leftSum == 0 ? nums.length - 1 : -1;
    }

    /**
     * 在排序数组中查找给定值的索引，若不存在该值，则返回需要插入的位置的索引
     * 击败：100%
     *
     * @param nums 已排序数组
     * @param target 目标值
     * @return 指定索引下标
     */
    public static int searchInsert(int[] nums, int target) {
        int LIdx = 0;
        int RIdx = nums.length - 1;
        while (LIdx < RIdx) {
            if (target > nums[(RIdx + LIdx) / 2]) {
                LIdx = (RIdx + LIdx) / 2 + 1;
            } else if (target < nums[(RIdx + LIdx) / 2]) {
                RIdx = (RIdx + LIdx) / 2;
            } else {
                return (RIdx + LIdx) / 2;
            }
        }
        return nums[RIdx] < target ? RIdx + 1 : RIdx;
    }
}
