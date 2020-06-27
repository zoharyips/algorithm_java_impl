package com.zohar;

import java.util.LinkedList;
import java.util.Stack;

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

    public static void main(String[] args) {
        System.out.println(reverseCharInWords("Let's take LeetCode contest"));
    }
}
