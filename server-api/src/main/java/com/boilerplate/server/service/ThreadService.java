package com.boilerplate.server.service;

import com.boilerplate.server.thread.CallableThread;
import com.boilerplate.server.thread.RunnableThread;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 多线程测试
 */
@Service
public class ThreadService {
    /**
     * 无返回值的多线程测试
     */
    public void runnableThread() {
        long currentTimeMillis = System.currentTimeMillis();

        //构建一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );

        RunnableThread runnableThread = new RunnableThread();
        for (int i = 1; i <= 10; i++) {
            threadPool.execute(runnableThread);
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

    /**
     * 有返回值的多线程测试
     */
    public void callableThread() {
        long currentTimeMillis = System.currentTimeMillis();

        //构建一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );

        CallableThread callableThread = new CallableThread();
        List<Future<List<Integer>>> tasks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            tasks.add(threadPool.submit(callableThread));
        }
        threadPool.shutdown();

        //关闭线程池
        try {
            while (!threadPool.awaitTermination(2, TimeUnit.SECONDS));
            for (Future<List<Integer>> task : tasks) {
                try {
                    System.out.println("返回值="+new Gson().toJson(task.get()));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("任务总耗时：" + (System.currentTimeMillis() - currentTimeMillis));
        }
    }
}
