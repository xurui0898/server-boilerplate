package com.boilerplate.server.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * 无返回值多线程-实现Runnable接口方式
 */
@Slf4j
public class RunnableThread implements Runnable {
    private int ticket = 50;

    @Override
    public void run() {
        int total = ticket;
        for (int i = 0; i < total; i++) {
            synchronized (this){
                if (ticket>0) {
                    log.info("RunnableThread 线程[{}]运行，num={}",Thread.currentThread().getName(),ticket);
                    ticket--;
                }
            }
        }
    }
}
