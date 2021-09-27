package com.boilerplate.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
public class LeetCodeController {
    public static void main(String[] args) {
        log.info("code test");
    }

    public int[] twoSum(int[] nums, int target) {
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
}
