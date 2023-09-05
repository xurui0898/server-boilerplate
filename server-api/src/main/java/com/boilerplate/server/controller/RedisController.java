package com.boilerplate.server.controller;

import com.boilerplate.server.utils.Response;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.redis.RedisUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Redis测试controller
 */
@RestController
@Slf4j
public class RedisController {
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("redis/test")
    public ApiResult<List<String>> test() {
        int teamId = 43;
        String teamNumKey = String.format("race_team_help_count_%s", teamId);
        String teamNumField = "help_count";
        String num = Objects.toString(redisUtils.hget(teamNumKey, teamNumField), "");
        String logText = String.format("redis data,team_id=%s team_help_num=%s", teamId,num);

        //test返回结果集封装
        List<String> logList = Lists.newArrayList(logText);
        ApiResult<List<String>> apiResult = Response.makeOKRsp(logList);

        log.info(new Gson().toJson(apiResult));
        return apiResult;
    }
}
