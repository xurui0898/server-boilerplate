package com.boilerplate.server.concurrent;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompleteFutureTest {
    public String getUserMobile(Long userId) {
        log.info("start getUserMobile...");
        ThreadUtil.sleep(1200);
        return "18678789977";
    }

    public String getUserAddress(Long userId) {
        log.info("start getUserAddress...");
        ThreadUtil.sleep(1500);
        return "北京市海淀区上地街道信息路";
    }

    public String getUserNickname(Long userId) {
        log.info("start getUserNickname...");
        ThreadUtil.sleep(1800);
        return "黑金会员浪黑猫";
    }
}
