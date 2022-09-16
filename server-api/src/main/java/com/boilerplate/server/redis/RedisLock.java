package com.boilerplate.server.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis setnx命令实现分布式锁
 */
@Component
public class RedisLock {
    @Autowired
    private RedisUtils redisUtils;

    private final static int TIME_OUT = 30;

    private final static TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    public boolean lockV1 (String key) {
        String id = UUID.randomUUID().toString() + Thread.currentThread().getName();
        LocalTime startLocalTime = LocalTime.now();

        while (true) {
            // 返回true，代表获取锁成功
            if (redisUtils.setnx(key, id, TIME_OUT, TIME_UNIT)) {
                return true;
            }

            // 否则循环等待，在timeout时间内仍未获取锁，则获取锁失败，返回false
            Duration duration = Duration.between(LocalTime.now(), startLocalTime);
            long seconds = duration.getSeconds();
            if (seconds >= TIME_OUT) {
                return false;
            }

            // 让线程休眠0.1秒后再尝试获取锁
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void unLockV1 (String key) {
        redisUtils.del(key);
    }

    public boolean lockV2 (String key, String id) {
        LocalTime startLocalTime = LocalTime.now();

        while (true) {
            // 返回true，代表获取锁成功
            if (redisUtils.setnx(key, id, TIME_OUT, TIME_UNIT)) {
                return true;
            }

            // 否则循环等待，在timeout时间内仍未获取锁，则获取锁失败，返回false
            Duration duration = Duration.between(LocalTime.now(), startLocalTime);
            long seconds = duration.getSeconds();
            if (seconds >= TIME_OUT) {
                return false;
            }

            // 让线程休眠0.1秒后再尝试获取锁
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void unLockV2 (String key, String id) {
        // 保证哪个线程加锁，就由哪个线程解锁，每一个线程用一个id对应，且id是锁key存储的value
        if (redisUtils.get(key).equals(id)) {
            redisUtils.del(key);
        }
    }

    public boolean unLockV3 (String key, String id) {
        // 拼接lua脚本
        String script =
                "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                        "   return redis.call('del',KEYS[1]) " +
                        "else" +
                        "   return 0 " +
                        "end";

        // 构建key参数列表
        List<String> keys = new ArrayList<>();
        keys.add(Collections.singletonList(key).toString());

        // 执行lua脚本
        Long result = redisUtils.executeLuaScript(script, Long.class, keys, id);
        return "1".equals(String.valueOf(result));
    }
}

