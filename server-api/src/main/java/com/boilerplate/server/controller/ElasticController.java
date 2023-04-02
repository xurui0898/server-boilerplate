package com.boilerplate.server.controller;

import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("elasticsearch")
public class ElasticController {
    @GetMapping("index")
    @ResponseBody
    public ApiResult<List<String>> index() {
        String logText = "elasticsearch";
        String logText2 = "kibana";
        //test返回结果集封装
        List<String> logList = Lists.newArrayList(logText,logText2);
        ApiResult<List<String>> apiResult = Response.makeOKRsp(logList);

        log.info(new Gson().toJson(apiResult));
        return apiResult;
    }
}
