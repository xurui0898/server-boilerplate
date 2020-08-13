package com.boilerplate.server.controller;

import com.boilerplate.server.dao.AccountMapper;
import com.boilerplate.server.dao.TestMapper;
import com.boilerplate.server.model.AccountExample;
import com.boilerplate.server.model.TestExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试一下
 */
@RestController
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
    public Object account() {
        AccountExample accountExample = new AccountExample();
        return accountMapper.selectByExample(accountExample);
    }

}
