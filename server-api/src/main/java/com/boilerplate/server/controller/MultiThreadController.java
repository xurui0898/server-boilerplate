package com.boilerplate.server.controller;

import com.boilerplate.server.concurrent.MultiThreadService;
import com.boilerplate.server.concurrent.entity.UserInfo;
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
        //CompleteFuture实现返回值多线程（推荐方式）
        UserInfo userInfo = multiThreadService.completeFutureTest();
        //countDownLatch实现返回值多线程
        UserInfo userInfo2 = multiThreadService.countDownLatchTest();

        log.info("多线程callableTest 返回结果={}",callableData);
        log.info("多线程completeFutureTest 返回结果={}",userInfo);
        log.info("多线程countDownLatchTest 返回结果={}",userInfo2);
    }
}
