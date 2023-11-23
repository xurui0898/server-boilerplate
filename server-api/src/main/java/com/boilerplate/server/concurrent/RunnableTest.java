package com.boilerplate.server.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * 无返回值多线程-实现Runnable接口方式
 */
@Slf4j
public class RunnableTest implements Runnable {
    private int ticket = 15;

    @Override
    public void run() {
        int total = ticket;
        for (int i = 0; i < total; i++) {
            synchronized (this){
                if (ticket>0) {
                    log.info("Runnable 运行，num={}",ticket);
                    ticket--;
                }
            }
        }
    }
}
