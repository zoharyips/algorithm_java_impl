package com.zohar.arithmetic;

import com.zohar.array.MultiArrays;

public class GreatestCommonDivisor {

    /**
     * 求最大公约数 - 辗转相除法 | 递归
     * 注：最大公约数不存在正负问题
     *
     * @param a 数字
     * @param b 数字
     * @return 最大公约数
     */
    public static int euclideanAlgorithm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        int max = Math.max(Math.abs(a), Math.abs(b)), min = Math.min(Math.abs(a), Math.abs(b));
        if (max % min == 0) return min;
        return euclideanAlgorithm(min, max % min);
    }

    /**
     * 求最大公约数 - 更相减损法 | 递归
     * @param a 数字
     * @param b 数字
     * @return 最大公约数
     */
    public static int decreasesTechnique(int a, int b) {
        if (a == 0 || b == 0) return 0;
        int max = Math.max(Math.abs(a), Math.abs(b)), min = Math.min(Math.abs(a), Math.abs(b));
        if ((max & 1) == 0 && (min & 1) == 0) return 2 * decreasesTechnique(max >> 1, min >> 1);
        if (max == min || max - min == min) return min;
        return decreasesTechnique(max - min, min);
    }

    /**
     * 相同质公因数解法
     * 介绍：获取两个数所有相同的质公因数，这些因数的乘积就是最大公约数
     *
     * @param a 数字
     * @param b 数字
     * @return 最大公约数
     */
    public static int samePrimerFactor(int a, int b) {
        if (a == 0 || b == 0) return 0;
        int max = Math.max(Math.abs(a), Math.abs(b)), min = Math.min(Math.abs(a), Math.abs(b));
        if (max % min == 0) return min;
        int[] samePrimerFactors = MultiArrays.sameInTwoSortedArrays(PrimerNumber.primerFactor(max), PrimerNumber.primerFactor(min));
        int res = 1;
        for (int i : samePrimerFactors) {
            res *= i;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(euclideanAlgorithm(10, 10)    + "\t:" + samePrimerFactor(10, 10));
        System.out.println(euclideanAlgorithm(10, 5)     + "\t:" + samePrimerFactor(10, 5));
        System.out.println(euclideanAlgorithm(10, 2)     + "\t:" + samePrimerFactor(10, 2));
        System.out.println(euclideanAlgorithm(10, 3)     + "\t:" + samePrimerFactor(10, 3));
        System.out.println(euclideanAlgorithm(10, 1)     + "\t:" + samePrimerFactor(10, 1));
        System.out.println(euclideanAlgorithm(10, 0)     + "\t:" + samePrimerFactor(10, 0));
        System.out.println(euclideanAlgorithm(-10, 100)  + "\t:" + samePrimerFactor(-10, 100));
        System.out.println(euclideanAlgorithm(81, -33)   + "\t:" + samePrimerFactor(81, -33));
        System.out.println(euclideanAlgorithm(615, 1997) + "\t:" + samePrimerFactor(615, 1997));
        System.out.println(euclideanAlgorithm(121, 1331) + "\t:" + samePrimerFactor(121, 1331));
    }
}
