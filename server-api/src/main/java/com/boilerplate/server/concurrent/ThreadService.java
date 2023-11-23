package com.boilerplate.server.concurrent;

import com.boilerplate.server.concurrent.CallableThread;
import com.boilerplate.server.concurrent.RunnableThread;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 多线程测试
 */
@Service
@Slf4j
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

        //关闭线程池，等待任务执行完成
        threadPool.shutdown();
        try {
            while (!threadPool.awaitTermination(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("任务总耗时：{}ms", System.currentTimeMillis() - currentTimeMillis);
        }
    }

    /**
     * 有返回值的多线程测试
     */
    public List<Integer> callableThread() {
        long currentTimeMillis = System.currentTimeMillis();
        List<Integer> data = new ArrayList<>();

        //构建一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );

        List<Integer> goodsIdList = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
        //按每2个一组分成多组
        List<Future<List<Integer>>> tasks = new ArrayList<>();
        List<List<Integer>> goodsIdLists = Lists.partition(goodsIdList, 2);
        for (List<Integer> idList : goodsIdLists) {
            CallableThread callableThread = new CallableThread(idList);
            tasks.add(threadPool.submit(callableThread));
        }

        //关闭线程池，等待任务执行完成
        threadPool.shutdown();
        try {
            while (!threadPool.awaitTermination(2, TimeUnit.SECONDS));
            for (Future<List<Integer>> task : tasks) {
                try {
                    log.info("返回值={}", task.get());
                    if (task.get() != null && !task.get().isEmpty()) {
                        data.addAll(task.get());
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("任务总耗时：{}ms", System.currentTimeMillis() - currentTimeMillis);
        }

        return data;
    }
}
