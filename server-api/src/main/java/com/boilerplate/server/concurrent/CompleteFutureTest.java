package com.boilerplate.server.concurrent;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompleteFutureTest {
    public String getUserMobile(Long userId) throws RuntimeException {
        log.info("start getUserMobile...");
        ThreadUtil.sleep(200);
        throw new RuntimeException("get mobile error");
        //return "18678789977";
    }

    public String getUserAddress(Long userId) {
        log.info("start getUserAddress...");
        ThreadUtil.sleep(200);
        return "北京市海淀区上地街道信息路";
    }

    public String getUserNickname(Long userId) {
        log.info("start getUserNickname...");
        ThreadUtil.sleep(300);
        return "黑金会员浪黑猫";
    }
}
