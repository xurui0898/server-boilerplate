package com.boilerplate.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 力扣测试controller
 */
@RestController
@Slf4j
public class LeetCodeController {
    public static void main(String[] args) {
        //无重复字符的最长子串 长度
        String str = "abcabeddcbb";
        int num = lengthOfLongestSubstring(str);
        System.out.println("无重复字符的最长子串 长度 = " + num);

        //字符串翻转
        String revStr = "Hello xu rui";
        String revStrData = reverseString(revStr);
        System.out.println("字符串翻转 = " + revStrData);
        //字符串翻转单词
        String revWords = "a good   example";
        String revWordsData = reverseWords(revWords);
        System.out.println("字符串翻转单词 = " + revWordsData);
        //字符串翻转单词3
        String revWords3 = "God Ding";
        String revWords3Data = reverseWords3(revWords3);
        System.out.println("字符串翻转单词3 = " + revWords3Data);
        //最长回文子串
        String longStr = "abbaabbw";
        String longStrData = longestPalindrome(longStr);
        System.out.println("最长回文子串 = " + longStrData);
    }

    /**
     * 字符串翻转
     * @param s
     * @return
     */
    public static String reverseString(String s) {
        char[] charArray = s.toCharArray();
        int length = charArray.length;
        int times = length / 2;
        for (int i = 0; i < times; i++) {
            char temp = charArray[i];
            charArray[i] = charArray[length - 1 - i];
            charArray[length-1-i] = temp;
        }
        return new String(charArray);
    }

    /**
     * 字符串翻转单词
     * 输入：s = "the sky is blue"
     * 输出："blue is sky the"
     * @param s
     * @return
     */
    public static String reverseWords(String s) {
        String[] sArr = s.trim().split(" ");//拆分为单词数组
        List<String> sList = Arrays.asList(sArr);//数组转list 这样转的list是不可变的，不能新增
        Collections.reverse(sList);//翻转list元素，每个元素里面还需要再去除头尾空格
        sList = sList.stream().filter(item-> !item.isEmpty()).collect(Collectors.toList());//去除空格元素 很重要
        return String.join(" ",sList);//使用空格拼接list为新的字符串
    }

    /**
     * 字符串翻转单词3
     * 输入： s = "God Ding"
     * 输出："doG gniD"
     * @param s
     * @return
     */
    public static String reverseWords3(String s) {
        String revStr = reverseString(s);
        revStr = reverseWords(revStr);
        return revStr;
    }

    /**
     * #3 无重复字符的最长子串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int left = 0,max = 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                left = Math.max(left,map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

    /**
     * 最长回文子串-中心扩散优化版
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        int start = 0, end = 0, strLen = s.length();
        int i = 0;
        while (i < strLen) {
            int left = i, right = i;
            //如果后面有连续相同字符，把right向右扩展到最后一个相同字符的位置，
            //并把i设置到该位置的下一个位置。
            //因为每次循环i左边的字符一定和i不相同，所以不需要将left向左扩展
            while (right < strLen - 1 && s.charAt(right + 1) == s.charAt(left)) {
                right++;
            }
            i = right + 1;

            while (left >= 0 && right < strLen && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }
            if (right - left> end - start) {
                end = right;
                start = left;
            }
        }
        //这里start+1是因为：双边扩散时为了判断更左字符-1了，实际开始位置是+1。
        //end不处理是因为substring不包含该元素，是结束元素
        return s.substring(start+1, end);
    }
}
