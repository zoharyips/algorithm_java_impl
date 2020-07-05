package com.zohar.arithmetic;

public class BaseMath {


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
}
