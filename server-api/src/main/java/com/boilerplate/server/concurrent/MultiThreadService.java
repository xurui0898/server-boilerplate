package com.boilerplate.server.concurrent;

import com.boilerplate.server.concurrent.entity.UserInfo;
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
public class MultiThreadService {
    /**
     * 无返回值的多线程测试
     */
    public void runnableTest() {
        long currentTimeMillis = System.currentTimeMillis();

        //构建一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );

        RunnableTest runnableTest = new RunnableTest();
        for (int i = 1; i <= 10; i++) {
            threadPool.execute(runnableTest);
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
     * 有返回值的多线程测试（使用Future获取返回值）
     */
    public List<Integer> callableTest() {
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
            CallableTest callableTest = new CallableTest(idList);
            tasks.add(threadPool.submit(callableTest));
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

    /**
     * 有返回值的多线程测试（使用completeFuture获取返回值）
     * @return
     */
    public UserInfo completeFutureTest() {
        long startTime = System.currentTimeMillis();
        Long userId = 1846L;

        //使用CompletableFuture实现多线程
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            CompleteFutureTest completeFutureTest = new CompleteFutureTest();

            //获取用户手机号
            CompletableFuture<Void> mobileFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return completeFutureTest.getUserMobile(userId);
                } catch (Exception e) {
                    return null;
                }
            }).thenAccept(userInfo::setMobile);
            //获取用户地址
            CompletableFuture<Void> addressFuture = CompletableFuture.supplyAsync(() -> {
                return completeFutureTest.getUserAddress(userId);
            }).thenAccept(userInfo::setAddress);
            //获取用户昵称
            CompletableFuture<Void> nicknameFuture = CompletableFuture.supplyAsync(() -> {
                return completeFutureTest.getUserNickname(userId);
            }).thenAccept(userInfo::setNickname);

            //使用allOf方法，线程都执行完成前一直阻塞
            CompletableFuture.allOf(mobileFuture, addressFuture, nicknameFuture).join();

            return userInfo;
        } finally {
            System.out.println("completeFutureTest 总共用时=" + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

    /**
     * countDownLatch实现返回值多线程
     * @return
     */
    public UserInfo countDownLatchTest() {
        long startTime = System.currentTimeMillis();
        Long userId = 1846L;
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            CompleteFutureTest completeFutureTest = new CompleteFutureTest();
            CountDownLatch countDownLatch = new CountDownLatch(3);

            //获取用户手机号
            executor.execute(() -> {
                try {
                    String mobile = completeFutureTest.getUserMobile(userId);
                    userInfo.setMobile(mobile);
                } catch (RuntimeException e) {
                } finally {
                    countDownLatch.countDown();
                }
            });
            //获取用户地址
            executor.execute(() -> {
                String address = completeFutureTest.getUserAddress(userId);
                userInfo.setAddress(address);
                countDownLatch.countDown();
            });
            //获取用户昵称
            executor.execute(() -> {
                String nickname = completeFutureTest.getUserNickname(userId);
                userInfo.setNickname(nickname);
                countDownLatch.countDown();
            });
            //唤醒主线程
            countDownLatch.await();

            return userInfo;
        } catch (InterruptedException e) {
            return null;
        } finally {
            //关闭线程池
            executor.shutdown();
            System.out.println("countDownLatchTest 总共用时=" + (System.currentTimeMillis() - startTime) + "ms");
        }
    }
}
