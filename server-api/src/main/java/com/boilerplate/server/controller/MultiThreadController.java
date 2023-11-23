package com.boilerplate.server.controller;

import com.boilerplate.server.concurrent.MultiThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 多线程测试controller
 */
@RestController
@Slf4j
public class MultiThreadController {

    public static void main(String[] args) {
        MultiThreadService multiThreadService = new MultiThreadService();

        //无返回值多线程
        multiThreadService.runnableTest();
        //有返回值多线程
        List<Integer> callableData = multiThreadService.callableTest();

        log.info("多线程返回结果={}",callableData);
    }
}
