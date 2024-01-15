package com.boilerplate.server.controller;

import com.boilerplate.server.test.TestService;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试专用new
 */
@Slf4j
public class TestNewController {

    public static void main(String[] args) {
        System.out.println("test new");
        TestService.removeList();
    }
}
