package com.boilerplate.server.controller;

import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.boilerplate.server.service.ThreadService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 多线程测试controller
 */
@RestController
@Slf4j
public class MultiThreadController {
    @Autowired
    private ThreadService threadService;

    /**
     * 多线程任务
     * @return
     */
    @RequestMapping("/multhread/test")
    public ApiResult<List<Integer>> test() {
        log.info("多线程任务开始...");
        //无返回值多线程
        threadService.runnableThread();
        //有返回值多线程
        List<Integer> callableData = threadService.callableThread();
        log.info("多线程任务结束...");

        ApiResult<List<Integer>> apiResult = Response.makeOKRsp(callableData);
        log.info("apiResult={}",new Gson().toJson(apiResult));
        return apiResult;
    }
}
