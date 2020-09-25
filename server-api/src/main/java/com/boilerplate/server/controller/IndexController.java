package com.boilerplate.server.controller;

import com.boilerplate.server.dao.AccountMapper;
import com.boilerplate.server.dao.TestMapper;
import com.boilerplate.server.entity.UserInfo;
import com.boilerplate.server.model.AccountExample;
import com.boilerplate.server.model.TestExample;
import com.boilerplate.server.thread.TestThread;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.*;

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
        long currentTimeMillis = System.currentTimeMillis();

        //构建一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
        TestThread testThread = new TestThread();

        System.out.println("任务start...");
        for (int i = 1; i <= 10; i++) {
            threadPool.execute(testThread);
        }
        threadPool.shutdown();

        //关闭线程池
        try {
            while (!threadPool.awaitTermination(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("任务总耗时：" + (System.currentTimeMillis() - currentTimeMillis));
        }
        System.out.println("任务完成...");

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1008611L);
        userInfo.setUserName("眺望流浪的时光");
        userInfo.setAddress("北京市海淀区");
        userInfo.setFeature("yep!");
        return userInfo;
    }

}
