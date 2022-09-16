package com.boilerplate.server.controller;

import com.boilerplate.server.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis测试专用
 */
@RestController
@Slf4j
public class RedisController {
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("redis/test")
    public String test() {
        int teamId = 43;
        String teamNumKey = String.format("race_team_help_count_%s", teamId);
        String teamNumField = "help_count";
        int num = Integer.parseInt(redisUtils.hget(teamNumKey, teamNumField).toString());

        String logText = String.format("redis data,team_id=%s team_help_num=%s", teamId,num);
        log.info(logText);
        return logText;
    }
}
