package com.zohar.array;

import java.util.*;

public class SingleArray {

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

    /**
     * 将数组（2n个元素）拆分成 n 对元素，返回每一对元素中较小元素的和，并使得和最大
     * 思路：先排序，再返回下标为奇数的元素的和
     * 击败：65%
     * 优化：基数排序
     *
     * @param nums 数组
     * @return sum(Min.(ai, bi))
     */
    public static int arrayPairMinSum(int[] nums) {
        java.util.Arrays.sort(nums);
        for (int i = 2; i < nums.length; i += 2) {
            nums[0] += nums[i];
        }
        return nums[0];
    }

    /**
     * 在已排序的数组中找到和为目标值的两个元素的索引，且要求索引从 1 开始
     * 思路：左右指针夹逼
     * 击败：100%
     *
     * @param nums 已排序数组
     * @param target 目标值
     * @return 二加数索引
     */
    public static int[] sortedTwoSum(int[] nums, int target) {
        if (nums.length < 2 || target < nums[0]) {
            return new int[0];
        }
        int lCursor = 0, rCursor = nums.length - 1;
        while (lCursor < rCursor) {
            if (nums[lCursor] + nums[rCursor] > target) {
                rCursor--;
            } else if (nums[lCursor] + nums[rCursor] < target) {
                lCursor++;
            } else {
                return new int[]{lCursor + 1, rCursor + 1};
            }
        }
        return new int[0];
    }

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
     * 四数之和，从数组中获取所有和为 target 的四数元组
     *
     * @param nums 数组
     * @param target 目标值
     * @return 所有和为 target 的四数元组
     */
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
     * 删除数组中所有指定数值的元素，且返回该数组操作后的长度
     * 思路：左右指针，通过交换的方式进行删除
     * 击败：100%
     *
     * @param nums 数组
     * @param val 待删除的数值
     * @return 删除操作后的长度
     */
    public static int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int lCursor = 0, rCursor = nums.length - 1, opCount = 0;
        while (lCursor < rCursor) {
            if (nums[lCursor] != val) {
                lCursor++;
                continue;
            }
            nums[lCursor] ^= nums[rCursor];
            nums[rCursor] ^= nums[lCursor];
            nums[lCursor] ^= nums[rCursor];
            rCursor--;
            opCount++;
        }
        return nums.length - opCount;
    }

    /**
     * 给定一二进制数组，查找出最长连续 1 的个数
     * 思路：快慢指针
     *
     * @param nums 二进制数组
     * @return 最长连续 1 的个数
     */
    public static int findMaxConsecutiveOnes(int[] nums) {
        int slowCursor = 0, res = 0;
        for (int fastCursor = 0; fastCursor < nums.length; fastCursor++) {
            slowCursor = nums[fastCursor] == 1
                    ? slowCursor == Integer.MAX_VALUE ? fastCursor : slowCursor
                    : Integer.MAX_VALUE;
            res = Math.max(fastCursor - slowCursor + 1, res);
        }
        return res;
    }

    /**
     * 给定一正整数数组，返回长度最小且和大于等于目标值的子数组的长度
     * 思路：前后指针动态窗口
     *
     * @param nums 正整数数组
     * @param s 目标值
     * @return 长度最小且和为目标值的子数组的长度，若无则返回 0
     */
    public static int minSubArrayLen(int[] nums, int s) {
        int lBorder = 0, rBorder = 0, windowSum = 0, res = Integer.MAX_VALUE;
        while (rBorder < nums.length && lBorder < nums.length) {
            if (nums[rBorder] > s) {
                return 1;
            }
            windowSum += nums[rBorder];
            if (windowSum < s) {
                rBorder++;
            } else {
                res = Math.min(rBorder - lBorder + 1, res);
                windowSum -= nums[lBorder++];
                windowSum -= nums[rBorder];
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    /**
     * 查找被旋转的已排序无重复元素数组中的最小值
     * 思路：由于是已排序数组，且被旋转，旋转的标志是最左值大于最右值
     * 如果 nums[0] > nums[len - 1]，返回 nums[0] 即可
     * 若不小于，则在 nums[1] 到 nums[len - 1] 之间使用二分法逐步缩小最小值范围
     *
     * @param nums 被旋转的已排序无重复数组
     * @return 数组中最小值
     */
    public static int findMinInReverseSortedArray(int[] nums) {
        if (nums.length < 2 || nums[0] < nums[nums.length - 1]) {
            return nums[0];
        }
        int L = 1, R = nums.length - 1;
        while (L < R) {
            if (R == L + 1) {
                return Math.min(nums[L], nums[R]);
            }
            if (nums[(R + L) / 2] > nums[R]) {
                L = (R + L) / 2 + 1;
            } else {
                R = (R + L) / 2;
            }
        }
        return nums[R];
    }

    /**
     * 求有序数组的中位数
     *
     * @param nums 有序数组
     * @return 中位数
     */
    public static double findMedianSortedArray(int[] nums) {
        return (nums.length & 1) == 0
                ? (double)(nums[nums.length / 2 - 1] + nums[nums.length / 2]) / 2
                : (double)nums[(1 + nums.length) / 2 - 1];
    }

    public static boolean isMatch(String s, String p) {
        if (s.length() == 0) {
            return p.length() == 0 || (p.length() > 1 && p.charAt(1) == '*');
        }
        if (p.length() == 0) {
            return false;
        }
        final int STATUS_CHAR = 1, STATUS_RAND = 2, STATUS_CHAR_STAR = 3, STATUS_RAND_STAR = 4;
        int currentStatus = 0, lastRandStatusCursor = -1, currentStatusCursor = -1, stringCursor = 0, patternCursor = 0;
        char currentRequiredChar = p.charAt(0), tmpCharS, tmpCharP;
        while (stringCursor < s.length() && patternCursor < p.length()) {
            /* 获取当前字符 */
            tmpCharS = s.charAt(stringCursor);
            if (tmpCharS < 'a' || tmpCharS > 'z') {
                return false;
            }
            /* 状态更新 */
            if (patternCursor != currentStatusCursor) {
                /* 如果当前状态是随机字符状态，则保存上次随机字符状态下标为当前状态下标 */
                if (currentStatus == STATUS_RAND_STAR) {
                    lastRandStatusCursor = currentStatusCursor;
                }
                /* 读取第一个字符 */
                tmpCharP = p.charAt(patternCursor++);
                if (tmpCharP >= 'a' && tmpCharP <= 'z') {
                    currentStatus = STATUS_CHAR;
                    currentRequiredChar = tmpCharP;
                } else if (tmpCharP == '.') {
                    currentStatus = STATUS_RAND;
                } else {
                    return false;
                }
                /* 读取第二个字符 */
                if (patternCursor == p.length()) {
                    if (stringCursor == s.length() - 1 && (currentStatus == STATUS_RAND || tmpCharS == currentRequiredChar)) {
                        return true;
                    } else {
                        if (lastRandStatusCursor > -1) {
                            currentStatus = STATUS_RAND_STAR;
                            currentStatusCursor = lastRandStatusCursor;
                            patternCursor = currentStatusCursor;
                            currentRequiredChar = p.charAt(patternCursor + 1);
                            continue;
                        } else {
                            return false;
                        }
                    }
                }
                tmpCharP = p.charAt(patternCursor);
                if (tmpCharP == '*') {
                    currentStatus = currentStatus == STATUS_CHAR ? STATUS_CHAR_STAR : STATUS_RAND_STAR;
                } else if (tmpCharP != '.' && !(tmpCharP >= 'a' && tmpCharP <= 'z')) {
                    return false;
                }
                /* 如果更新后状态并不是连续匹配状态，将当前状态下标加一 */
                if (currentStatus == STATUS_CHAR || currentStatus == STATUS_RAND) {
                    patternCursor--;
                    currentStatusCursor = patternCursor;
                } else {
                    /* 如果是连续匹配状态，则将 currentRequiredChar 设置为终止连续匹配的字符 */
                    currentStatusCursor += 2;
                    /* 可以直接匹配到最后 */
                    if (patternCursor + 1 == p.length()) {
                        if (currentStatus != STATUS_RAND_STAR) {
                            for (int i = stringCursor; i < s.length(); i++) {
                                if (currentRequiredChar != s.charAt(i)) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                    currentRequiredChar = currentStatus == STATUS_RAND_STAR ? p.charAt(currentStatusCursor + 1) : currentRequiredChar;
                }
            }

            switch (currentStatus) {
                case STATUS_CHAR:
                    if (tmpCharS == currentRequiredChar) {
                        stringCursor++;
                        patternCursor++;
                    } else {
                        if (lastRandStatusCursor == -1) {
                            return false;
                        } else {
                            currentStatus = STATUS_RAND_STAR;
                            currentStatusCursor = lastRandStatusCursor;
                            patternCursor = currentStatusCursor;
                            currentRequiredChar = p.charAt(currentStatusCursor + 1);
                        }
                    }
                    break;
                case STATUS_CHAR_STAR:
                    if (tmpCharS != currentRequiredChar) {
                        patternCursor++;
                    } else {
                        stringCursor++;
                    }
                    break;
                case STATUS_RAND:
                    patternCursor++;
                    stringCursor++;
                    break;
                case STATUS_RAND_STAR:
                    if (currentRequiredChar == tmpCharS) {
                        patternCursor += 2;
                    }
                    stringCursor++;
                    break;
            }
        }
        if (stringCursor == s.length() && patternCursor == p.length()) {
            return true;
        } else {
            if (currentStatus == STATUS_CHAR_STAR) {
                /* 把所有可以去掉的字符去掉 */
                /* 倒着匹配 */
                int counter = 1;
                for (int i = p.length() - 1; i > patternCursor; i--) {
                    if (p.charAt(i) == '*') {
                        i--;
                        continue;
                    }
                    if (p.charAt(i) != currentRequiredChar || s.charAt(s.length() - counter--) != p.charAt(i)) {
                        return false;
                    }
                }
                return true;
            } else if (currentStatus == STATUS_RAND_STAR) {
                return patternCursor == p.length() - 1;
            } else {
                for (int i = p.length() - 1; i >= patternCursor; i--) {
                    if (p.charAt(i) == '*') {
                        i--;
                        continue;
                    }
                    return false;
                }
                return true;
            }
        }
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
        System.out.println(isMatch("abc", "a*b*c*.*abca*b*c*.*"));
    }
}
