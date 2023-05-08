package com.boilerplate.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认controller
 */
@RestController
@Slf4j
public class IndexController {
    public static void main(String[] args) {
        System.out.println("Test 默认测试");
    }
}
