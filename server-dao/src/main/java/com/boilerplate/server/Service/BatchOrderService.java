package com.boilerplate.server.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.boilerplate.server.dao.UserOrderMapper;
import com.boilerplate.server.entity.order.OrderVo;
import com.boilerplate.server.model.UserOrder;
import com.boilerplate.server.model.UserOrderExample;
import com.boilerplate.server.sharding.ShardingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 订单批量查询
 */
@Service
@DS(ShardingUtils.SHARDING_DATA_SOURCE_NAME)
public class BatchOrderService {
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private TestOrderService testOrderService;
    @Resource(name = "batchOrderPool")
    private ThreadPoolTaskExecutor orderQueryPool;


    /**
     * 根据订单号批量查询订单
     * @param orderIds
     * @return
     */
    public List<OrderVo> batchQueryOrder(List<Long> orderIds) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(orderIds.size());

        List<OrderVo> orderVos = new CopyOnWriteArrayList<>();
        orderIds.forEach(orderId->{
            orderQueryPool.execute(() -> {
                //指定子线程切换数据源
                DynamicDataSourceContextHolder.push(ShardingUtils.SHARDING_DATA_SOURCE_NAME);
                orderVos.add(testOrderService.queryOrder(orderId));
                countDownLatch.countDown();
            });
        });
        countDownLatch.await();

        return orderVos;
    }

    /**
     * 批量查询订单测试
     * @param orderIds
     * @return
     */
    public List<UserOrder> batchQueryTest(List<Long> orderIds) {
        UserOrderExample example = new UserOrderExample();
        UserOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdIn(orderIds);

        return userOrderMapper.selectByExample(example);
    }
}
