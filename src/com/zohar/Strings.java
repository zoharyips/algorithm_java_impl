package com.zohar;

import java.util.HashMap;
import java.util.LinkedList;

public class Strings {

    /**
     * 反转字符串中的单词顺序
     *
     * @param s 字符串
     * @return 操作后的字符串
     */
    public static String reverseWords(String s) {
        if ("".equals(s)) {
            return "";
        }
        LinkedList<String> linkedList = new LinkedList<>();
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            if (aChar != ' ') {
                if (stringBuilder.length() == 1 && stringBuilder.charAt(0) == ' ') {
                    linkedList.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
                stringBuilder.append(aChar);
            } else {
                if (stringBuilder.length() >= 1 && stringBuilder.charAt(0) != ' ') {
                    linkedList.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                    stringBuilder.append(' ');
                }
            }
        }
        if (stringBuilder.length() > 0) {
            if (stringBuilder.charAt(0) != ' ') {
                linkedList.add(stringBuilder.toString());
            }
        }
        stringBuilder.setLength(0);
        while (linkedList.size() > 0) {
            stringBuilder.append(linkedList.pollLast());
        }
        return stringBuilder.toString();
    }

    /**
     * 反转字符串中每个单词的字母
     * 思路 1：利用栈
     * 思路 2：本地交换
     *
     * @param s 字符串
     * @return 处理后的字符串
     */
    public static String reverseCharInWords(String s) {
        if (s.length() == 0 || s.length() == 1) {
            return s;
        }
        char[] chars = s.toCharArray();
        char tempChar;
        for (int lCursor = 0, rCursor = 0; rCursor < chars.length; rCursor++) {
            if (s.charAt(rCursor) == ' ') {
                for (int i = lCursor, j = rCursor - 1; i < j; i++, j--) {
                    tempChar = chars[j];
                    chars[j] = chars[i];
                    chars[i] = tempChar;
                }
                lCursor = rCursor + 1;
            } else if (rCursor == chars.length - 1) {
                for (int i = lCursor, j = rCursor; i < j; i++, j--) {
                    tempChar = chars[j];
                    chars[j] = chars[i];
                    chars[i] = tempChar;
                }
            }
        }
        return String.valueOf(chars);
    }

    /**
     * 将整型转化为罗马数字
     * 击败：85%
     *
     * @param num 正整数
     * @return 罗马数字字符串
     */
    public static String intToRoman(int num) {
        StringBuilder res = new StringBuilder();
        int quotient, divisor = 1000;
        String character;
        while (num > 0) {
            if (num >= 1000) {
                divisor = 1000;
                character = "M";
            } else if (num >= 900) {
                divisor = 900;
                character = "CM";
            } else if (num >= 500) {
                divisor = 500;
                character = "D";
            } else if (num >= 400) {
                divisor = 400;
                character = "CD";
            } else if (num >= 100) {
                divisor = 100;
                character = "C";
            } else if (num >= 90) {
                divisor = 90;
                character = "XC";
            } else if (num >= 50) {
                divisor = 50;
                character = "L";
            } else if (num >= 40) {
                divisor = 40;
                character = "XL";
            } else if (num >= 10) {
                divisor = 10;
                character = "X";
            } else if (num >= 9) {
                divisor = 9;
                character = "IX";
            } else if (num >= 5) {
                divisor = 5;
                character = "V";
            } else if (num >= 4) {
                divisor = 4;
                character = "IV";
            } else {
                divisor = 1;
                character = "I";
            }
            quotient = num / divisor;
            num = num % divisor;
            res.append(character.repeat(quotient));
        }
        return res.toString();
    }

    public static void main(String[] args) {
        System.out.println(reverseCharInWords("Let's take LeetCode contest"));
    }
}
