package com.boilerplate.server.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 有返回值多线程-实现Callable接口方式
 */
@Slf4j
public class CallableThread implements Callable<List<Integer>> {
    private final List<Integer> goodsIdList;

    public CallableThread(List<Integer> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    @Override
    public List<Integer>  call() throws Exception {
        List<Integer> data = new ArrayList<>();
        for (Integer goodsId : goodsIdList) {
            //业务处理 单个模拟耗时
            Thread.sleep(200);

            data.add(goodsId);
            log.info("处理完成，goodsId={}",goodsId);
        }
        return data;
    }
}
