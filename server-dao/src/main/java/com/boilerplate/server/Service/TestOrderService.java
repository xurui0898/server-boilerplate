package com.boilerplate.server.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.boilerplate.server.dao.UserOrderItemMapper;
import com.boilerplate.server.dao.UserOrderMapper;
import com.boilerplate.server.model.UserOrder;
import com.boilerplate.server.sharding.RandomUtils;
import com.boilerplate.server.sharding.ShardingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
@DS(ShardingUtils.SHARDING_DATA_SOURCE_NAME)
public class TestOrderService {
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private UserOrderItemMapper userOrderItemMapper;
    @Autowired
    private RandomUtils randomUtils;

    /**
     * 创建测试订单
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder() {
        Long userId = randomUtils.getUserId();
        Long shopId = randomUtils.getShopId();
        Long orderId = ShardingUtils.genOrderId(userId);
        //订单表
        UserOrder order = new UserOrder();
        order.setOrderId(orderId);
        order.setShopId(shopId);
        order.setCustomerId(userId);
        order.setCustomerName(randomUtils.getName());
        order.setCustomerMobile(randomUtils.getMobile());
        order.setCustomerCity(101);
        order.setCustomerAddress(randomUtils.getAddress());
        order.setOrderPrice(new BigDecimal("131.98"));
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        userOrderMapper.insertSelective(order);
        return order.getOrderId();
    }

}
