package com.boilerplate.server.controller;

import com.boilerplate.server.dao.AccountMapper;
import com.boilerplate.server.entity.UserInfo;
import com.boilerplate.server.model.AccountExample;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * mysql测试controller
 */
@RestController
@Slf4j
@RequestMapping("mysql")
public class MysqlController {
    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("account")
    @ResponseBody
    public Object account(UserInfo userInfo) {
        //工具类创建测试list
        List<Long> idList = Lists.newArrayList(1L, 3L, 8L, 9L, 10L, 11L);
        log.info(new Gson().toJson(idList));
        log.info(String.valueOf(userInfo.getUserId()));

        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        criteria.andIdIn(idList);
        return accountMapper.selectByExample(accountExample);
    }
}
