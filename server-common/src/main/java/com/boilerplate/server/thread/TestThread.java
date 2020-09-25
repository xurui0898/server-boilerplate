package com.boilerplate.server.thread;

/**
 * 测试多线程-实现Runnable接口方式
 */
public class TestThread implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {
        int total = ticket;
        for (int i = 0; i < total; i++) {
            synchronized (this){
                if (ticket>0) {
                    String logText = String.format("线程[%s]运行，ticket=%s", Thread.currentThread().getName(), ticket);
                    System.out.println(logText);
                    ticket--;

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
