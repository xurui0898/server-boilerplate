package com.boilerplate.server.controller;

import com.boilerplate.server.dao.AccountMapper;
import com.boilerplate.server.dao.TestMapper;
import com.boilerplate.server.entity.UserInfo;
import com.boilerplate.server.model.AccountExample;
import com.boilerplate.server.model.TestExample;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试一下
 */
@RestController
@Slf4j
public class IndexController {
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private AccountMapper accountMapper;

    @RequestMapping("/")
    public Object index() {
        TestExample testExample = new TestExample();
        testExample.setLimitStart(1);
        testExample.setLimitEnd(1);
        return testMapper.selectByExample(testExample);
    }

    @RequestMapping("account")
    public Object account(UserInfo userInfo) {
        log.info(String.valueOf(userInfo.getUserId()));
        log.info(userInfo.getUserName());
        //工具类测试
        List<Long> idList = Lists.newArrayList(1L, 3L, 8L, 9L, 10L, 11L);

        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        criteria.andIdIn(idList);
        return accountMapper.selectByExample(accountExample);
    }

    @RequestMapping("test")
    public Object test() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(10086L);
        userInfo.setUserName("眺望流浪的时光");

        log.info(new Gson().toJson(userInfo));
        return new Gson().toJson(userInfo);
    }

}
