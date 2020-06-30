package com.zohar;

import java.util.*;

public class Arrays {

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
            return findMedianSortedArray(availableNums);
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

    public static void main(String[] args) {
        System.out.println(isMatch("abc", "a*b*c*.*abca*b*c*.*"));
    }
}
