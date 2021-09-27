package com.boilerplate.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;

@RestController
@Slf4j
public class LeetCodeController {
    public static void main(String[] args) {
        log.info("code test");
        String s = "abcabeddcbb";
        int num = lengthOfLongestSubstring(s);
        System.out.println("num = " + num);
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] indexData = new int[2];
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(nums[i])) {
                indexData[0] = i;
                indexData[1] = hashMap.get(nums[i]);
                return indexData;
            }
            hashMap.put(target - nums[i], i);
        }
        return indexData;
    }

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
}
