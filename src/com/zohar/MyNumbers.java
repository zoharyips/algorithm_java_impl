package com.zohar;

import java.util.*;
import java.util.Arrays;

public class MyNumbers {

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
     * 整数除法
     * 思路：考虑三个问题：
     * 1. 截取小数
     * 2. 正负问题
     * 3. 超出整型边界问题
     * 4. 除零问题
     *
     * @param dividend 被除数
     * @param divisor 除数
     * @return 商
     */
    public static int divide(int dividend, int divisor) {
        /* 解决除零问题 */
        assert divisor != 0;

        /* 零作为分子 */
        if (dividend == 0) {
            return 0;
        }

        /* 解决正负问题：先保存结果正负，将操作数转化为 long 正整数 */
        boolean negativeRes = false;
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)) {
            negativeRes = true;
        }

        /* 将操作数转化为 long 正整数 */
        long longDividend = Math.abs((long) dividend);
        long longDivisor = Math.abs((long) divisor);

        if (longDividend == longDivisor) {
            return negativeRes ? -1 : 1;
        } else if (longDividend < longDivisor){
            return 0;
        }

        Boolean isSuitable = false;

        long[] res = divideHelper(longDividend, longDivisor);
        if (!negativeRes && res[0] > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return negativeRes ? -(int)res[0] : (int)res[0];
    }

    /**
     * Long 整型除法
     * 返回值第二个数：
     * 1 整除
     * 非 1：非整除
     *
     * @param dividend 被除数
     * @param divisor 除数
     * @return 结果
     */
    private static long[] divideHelper(long dividend, long divisor) {
        long counter = 1;
        long current = divisor;
        if (dividend < divisor) {
            return new long[]{0, 0};
        }
        while (current < dividend) {
            counter += counter;
            current += current;
        }
        if (current == dividend) {
            return new long[]{counter, 1};
        } else {
            long[] res = divideHelper(current - dividend, divisor);
            return res[1] == 1 ? new long[]{counter - res[0], 0} : new long[]{counter - res[0] - 1, 0};
        }
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) {
            return Collections.emptyList();
        }

        /* 将每两位相加的结果保存为 key，值为两个加数对应的值数组，因为有多个元素对相加的结果相同 */
        HashMap<Integer, ArrayList<int[]>> twoSumTwoIdxMap = new HashMap<>();
        int key;
        ArrayList<int[]> val;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                key = nums[i] + nums[j];
                if (!twoSumTwoIdxMap.containsKey(key)) {
                    val = new ArrayList<>();
                } else {
                    val = twoSumTwoIdxMap.get(key);
                }
                val.add(new int[]{i, j});
                twoSumTwoIdxMap.put(key, val);
            }
        }

        /* 让 twoSumTwoIdxMap 中的 Key 有序 */
        int idx = 0;
        int[] orderedTwoSums = new int[twoSumTwoIdxMap.size()];
        for (Integer integer : twoSumTwoIdxMap.keySet()) {
            orderedTwoSums[idx++] = integer;
        }

        Arrays.sort(orderedTwoSums);

        HashMap<Integer, Integer> twoSumTargetIdxMap = new HashMap<>(orderedTwoSums.length);
        for (int i = 0; i < orderedTwoSums.length; i++) {
            twoSumTargetIdxMap.put(orderedTwoSums[i], i);
        }

        /* 二数相加 */
        int targetTwoSumIdx;
        ArrayList<int[]> listOne;
        ArrayList<int[]> listTwo;
        List<List<Integer>> res = new ArrayList<>();
        List<Set<Integer>> addedRecord = new ArrayList<>();
        for (int i = 0; i < orderedTwoSums.length; i++) {
            /* 如果没有数能与当前 twoSum 相加组成结果，跳过 */
            if (!twoSumTargetIdxMap.containsKey(target - orderedTwoSums[i])) {
                continue;
            }
            /* 当前 twoSum 有其他 twoSum 可以相加组成结果, targetTwoSunIdx 为 orderedTwoSums 中的 idx */
            targetTwoSumIdx = twoSumTargetIdxMap.get(target - orderedTwoSums[i]);
            /* 如果目标数索引比当前索引小，已经匹配过了，跳过 */
            if (targetTwoSumIdx < i) {
                continue;
            }
            /* 如果目标数索引和当前索引相同，尝试与相同 twoSum 值而不同内容的自己组 */
            listOne = twoSumTwoIdxMap.get(orderedTwoSums[targetTwoSumIdx]);
            if (targetTwoSumIdx == i) {
                if (listOne.size() < 2) {
                    continue;
                }
                for (int j = 0; j + 1 < listOne.size(); j++) {
                    int[] tmp1 = listOne.get(j);
                    int[] tmp2 = listOne.get(j + 1);
                    if (!(tmp1[0] == tmp2[0] || tmp1[0] == tmp2[1] || tmp1[1] == tmp2[0] || tmp1[1] == tmp2[1])) {
                        List<Integer> record = new ArrayList<>(4);
                        record.add(nums[tmp1[0]]);
                        record.add(nums[tmp1[1]]);
                        record.add(nums[tmp2[0]]);
                        record.add(nums[tmp2[1]]);
                        Set<Integer> set = new HashSet<>(4);
                        set.addAll(record);
                        if (addedRecord.contains(set)) {
                            continue;
                        }
                        addedRecord.add(set);
                        res.add(record);
                    }
                }
            }
            /* 目标索引大于当前索引，直接强行组合 */
            listTwo = twoSumTwoIdxMap.get(orderedTwoSums[i]);
            for (int[] listOneVal : listOne) {
                for (int[] listTwoVal : listTwo) {
                    if (!(listOneVal[0] == listTwoVal[0]
                            || listOneVal[0] == listTwoVal[1]
                            || listOneVal[1] == listTwoVal[0]
                            || listOneVal[1] == listTwoVal[1])) {
                        List<Integer> record = new ArrayList<>(4);
                        record.add(nums[listOneVal[0]]);
                        record.add(nums[listOneVal[1]]);
                        record.add(nums[listTwoVal[0]]);
                        record.add(nums[listTwoVal[1]]);
                        Set<Integer> set = new HashSet<>(4);
                        set.addAll(record);
                        if (addedRecord.contains(set)) {
                            continue;
                        }
                        addedRecord.add(set);
                        res.add(record);
                    }
                }
            }
        }
        return res;
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
        System.out.println(divide(Integer.MAX_VALUE, -10));
    }
}