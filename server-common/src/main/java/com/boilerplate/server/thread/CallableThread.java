package com.boilerplate.server.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 有返回值多线程-实现Callable接口方式
 */
public class CallableThread implements Callable<List<Integer>> {
    private int num = 50;

    @Override
    public List<Integer>  call() throws Exception {
        List<Integer> data = new ArrayList<>();
        int total = num;
        for (int i = 0; i < total; i++) {
            synchronized (this){
                if (num >0) {
                    String logText = String.format("CallableThread 线程[%s]运行，num=%s", Thread.currentThread().getName(), num);
                    System.out.println(logText);
                    //模拟返回错误数据
                    if (num == 6 || num == 25 || num == 48 || num == 50) {
                        data.add(num);
                    }
                    num--;
                }
            }
        }

        return data;
    }
}
