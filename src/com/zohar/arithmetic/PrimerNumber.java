package com.zohar.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrimerNumber {

    /**
     * 暴力判断质数
     *
     * @param num 数字
     * @return 若为质数返回 true
     */
    public static boolean isPrimerForce(int num) {
        if (num < 2) return false;
        for (int i = 2; i < num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    /**
     * 借助筛法的思想判定一个数是否是素数
     * 逐步获取质数，直至 sqrt(num)，每获取一个质数，则判断 num 能否被该质数整除，可以则 num 不是质数
     *
     * @param num 数字
     * @return 是否是质数
     */
    public static boolean sieveOfEratosthenes(int num) {
        if (num < 2)        return false;
        if (num == 2)       return true;
        if ((num & 1) == 0) return false;
        boolean[] isNotPrimer = new boolean[(int) Math.sqrt(num) + 1];
        for (int i = 3; i < isNotPrimer.length; i += 2) {
            if (!isNotPrimer[i]) {
                for (int j = 3; i * j < isNotPrimer.length; j += 2) {
                    isNotPrimer[i * j] = false;
                }
            }
        }
        for (int i = 3; i < isNotPrimer.length; i += 2) {
            if (!isNotPrimer[i]) {
                if (num % i == 0) return false;
            }
        }
        return true;
    }

    /**
     * 分解质因数
     * 注：1 和 本身 不算质因数
     *
     * @param num 数字
     * @return 质因数数组
     */
    public static int[] primerFactor(int num) {
        int x = num;
        if (x < 4) return new int[0];
        List<Integer> tmpRes = new ArrayList<>();
        /* 将合数从偶数转化为奇数，避免下文过多对于 2 的计算 */
        while ((x & 1) == 0) {
            tmpRes.add(2);
            x /= 2;
        }
        /* 筛法求质数表 */
        boolean[] isNotPrimer = new boolean[(int) Math.sqrt(x) + 1];
        for (int i = 3; i < isNotPrimer.length; i += 2) {
            if (isNotPrimer[i]) continue;
            for (int j = 3; i * j < isNotPrimer.length; j += 2) {
                isNotPrimer[i * j] = false;
            }
        }
        /* 判断质数公因数 */
        for (int i = 3; i < isNotPrimer.length; i += 2) {
            if (isNotPrimer[i] || x % i != 0) continue;
            tmpRes.add(i);
            x /= i;
            i -= 2;
        }
        /* 如果已经开过公因数了，那么最终剩下的也是公因数 */
        if (x != num && x != 1) tmpRes.add(x);
        int[] res = new int[tmpRes.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = tmpRes.get(i);
        }
        return res;
    }

    /**
     * 计算小于 n 的质数的个数，不包含 n
     *
     * @param n 数字
     * @return 小于 n 的质数的个数
     */
    public static int countPrimes(int n) {
        if (n <= 2) return 0;
        boolean[] isNotPrimer = new boolean[n];
        for (int i = 3; i < isNotPrimer.length; i += 2) {
            if (!isNotPrimer[i]) {
                for (int j = 3; i * j < isNotPrimer.length; j += 2) {
                    isNotPrimer[i * j] = true;
                }
            }
        }
        n = 1;
        for (int i = 3; i < isNotPrimer.length; i += 2) {
            if (!isNotPrimer[i]) {
                n++;
            }
        }
        return n;
    }
}
