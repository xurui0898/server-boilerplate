package com.boilerplate.server.controller;

import com.boilerplate.server.service.ThreadService;
import com.boilerplate.server.dao.AccountMapper;
import com.boilerplate.server.dao.TestMapper;
import com.boilerplate.server.entity.UserInfo;
import com.boilerplate.server.model.AccountExample;
import com.boilerplate.server.model.TestExample;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private ThreadService threadService;

    @RequestMapping("/")
    public Object index() {
        TestExample testExample = new TestExample();
        testExample.setLimitStart(1);
        testExample.setLimitEnd(1);
        return testMapper.selectByExample(testExample);
    }

    @RequestMapping("/account")
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

    @RequestMapping("/thread")
    public Object thread() {
        log.info("多线程任务开始...");
        //无返回值多线程
        threadService.runnableThread();
        //有返回值多线程
        List<Integer> callableData = threadService.callableThread();
        System.out.println("所有返回值="+callableData);
        log.info("多线程任务结束...");

        return callableData;
    }

    @RequestMapping("/test")
    public Object test() {
        //日期转换为时间戳
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());
        long formatTime = 0;
        try {
            formatTime = dateFormat.parse(currentDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("原日期时间={}，转换后时间戳={}",currentDate,formatTime);

        //时间戳转换为日期
        long currentTime = System.currentTimeMillis();
        String formatDate = dateFormat.format(new Date(currentTime));
        log.info("原时间戳={}，转换后日期时间={}",currentTime,formatDate);
        return true;
    }

}
