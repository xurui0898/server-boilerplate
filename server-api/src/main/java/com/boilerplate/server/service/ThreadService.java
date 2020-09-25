package com.boilerplate.server.service;

import com.boilerplate.server.thread.TestThread;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 多线程测试
 */
@Service
public class ThreadService {
    /**
     * 计数任务
     */
    public void taskNum() {
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
    }
}
