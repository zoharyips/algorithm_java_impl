package com.zohar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Numbers {

    /**
     * 最接近的三数之和
     *
     * @param nums 数集
     * @param target 目标值
     * @return 最接近的元素
     */
    public static int threeSumClosest(int[] nums, int target) {
        /* 排序 */
        Arrays.sort(nums);

        int MIN_VAL = nums[0] + nums[1] + nums[2];
        int MAX_VAL = nums[nums.length - 1] + nums[nums.length - 2] + nums[nums.length - 3];
        int currentClosest = Integer.MAX_VALUE;
        int currentClosestGap = Integer.MAX_VALUE;
        int currentVal = MIN_VAL;

        if (target > MAX_VAL) {
            return MAX_VAL;
        }

        /* 每个元素最接近的值的下标, -3000 元素所对应的最接近元素下标必定是 0 */
        int[] closestValIdx = new int[6001];
        int gap1, gap2;
        for (int i = 1; i < closestValIdx.length; i++) {
            if (closestValIdx[i - 1] == nums.length - 1) {
                closestValIdx[i] = closestValIdx[i - 1];
            } else {
                gap1 = Math.abs(i - 3000 - nums[closestValIdx[i - 1]]);
                gap2 = Math.abs(i - 3000 - nums[closestValIdx[i - 1] + 1]);
                closestValIdx[i] = gap1 < gap2 ? closestValIdx[i - 1] : closestValIdx[i - 1] + 1;
            }
        }

        int destination, desIdx;
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                destination = target - nums[i] - nums[j];
                if (destination < -3000) {
                    return currentVal;
                }
                if (destination > 3000) {
                    continue;
                }
                desIdx = closestValIdx[destination + 3000];
                while (desIdx <= j && desIdx != nums.length - 1) {
                    desIdx++;
                }
                if (desIdx <= j) {
                    continue;
                }
                currentVal = nums[i] + nums[j] + nums[desIdx];
                if (currentClosestGap > Math.abs(target - currentVal)) {
                    currentClosest = currentVal;
                    currentClosestGap = Math.abs(target - currentVal);
                    if (currentClosestGap == 0) {
                        return currentClosest;
                    }
                }
            }
        }
        return currentClosest;
    }

    /**
     * 获取一个随机的数组，长度随机，元素随机
     *
     * @param minLen 最小数组长度
     * @param maxLen 最长数组长度
     * @param negativable 是否有负元素
     * @param minNum 最小负元素
     * @param maxNum 最大负元素
     * @return 满足要求的随机数组
     */
    public static int[] randNums(int minLen, int maxLen, boolean negativable, int minNum, int maxNum) {
        if (!negativable && minNum < 0) {
            minNum = 0;
        }
        if (negativable && minNum > 0) {
            negativable = false;
        }

        int effectiveRange = maxNum - minNum;
        int positiveMinNum = Math.abs(minNum);

        Random random = new Random(System.currentTimeMillis());

        int len = random.nextInt(maxLen);
        while (len < minLen) {
            len = random.nextInt(maxLen);
        }
        int[] res = new int[len];

        for (int i = 0; i < res.length; i++) {
            res[i] = random.nextInt(effectiveRange);
            res[i] -= negativable ? positiveMinNum : 0;
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(threeSumClosest(new int[]{0,1,2}, 0));
    }
}
