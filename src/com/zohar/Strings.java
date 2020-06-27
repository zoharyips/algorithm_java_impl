package com.zohar;

import java.util.LinkedList;
import java.util.Stack;

public class Strings {

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
}
