package com.boilerplate.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {
    // 获取服务器的cpu个数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 创建线程池-批量查询订单
     * @return
     */
    @Bean(name = "batchOrderPool")
    public ThreadPoolTaskExecutor batchOrderPool() {
        int corePoolSize = CPU_COUNT * 2;
        int maximumPoolSize = corePoolSize * 2;
        int keepAliveTime = 60;

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数：核心线程一直存活
        executor.setCorePoolSize(corePoolSize);
        // 最大线程数
        executor.setMaxPoolSize(maximumPoolSize);
        // 线程队列容量：任务将队列塞满之后，扩展核心线程，线程总数最多不超过最大线程数
        executor.setQueueCapacity(maximumPoolSize*2);
        // 闲置线程存活时长
        executor.setKeepAliveSeconds(keepAliveTime);
        // 拒绝策略：任务超出线程池容量后，新任务交还主线程处理
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程名前缀
        executor.setThreadNamePrefix("batch-order-worker-");
        // 初始化线程池
        executor.initialize();
        return executor;
    }
}
