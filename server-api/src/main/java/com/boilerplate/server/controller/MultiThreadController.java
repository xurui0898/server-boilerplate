package com.boilerplate.server.controller;

import com.boilerplate.server.concurrent.ThreadService;
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
        ThreadService threadService = new ThreadService();

        log.info("多线程任务开始...");
        //无返回值多线程
        threadService.runnableThread();
        //有返回值多线程
        List<Integer> callableData = threadService.callableThread();
        log.info("多线程任务结束...");

        log.info("apiResult={}",callableData);
    }
}
