package com.boilerplate.server.sharding;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.boilerplate.server.Service.OrderIdService;
import com.boilerplate.server.Service.RandomService;
import com.boilerplate.server.dao.UserOrderItemMapper;
import com.boilerplate.server.dao.UserOrderMapper;
import com.boilerplate.server.model.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
@DS(ShardingConstants.SHARDING_DATA_SOURCE_NAME)
public class OrderService {
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private UserOrderItemMapper userOrderItemMapper;
    @Autowired
    private RandomService randomService;

    /**
     * 创建测试订单
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createTestOrder() {
        Long userId = randomService.getUserId();
        Long shopId = randomService.getShopId();
        Long orderId = OrderIdService.genOrderId(userId);
        //订单表
        UserOrder order = new UserOrder();
        order.setOrderId(orderId);
        order.setShopId(shopId);
        order.setCustomerId(userId);
        order.setCustomerName(randomService.getName());
        order.setCustomerMobile(randomService.getMobile());
        order.setCustomerCity(101);
        order.setCustomerAddress(randomService.getAddress());
        order.setOrderPrice(new BigDecimal("131.98"));
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        userOrderMapper.insertSelective(order);
        return order.getOrderId();
    }

}
