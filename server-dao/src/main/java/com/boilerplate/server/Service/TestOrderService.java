package com.boilerplate.server.Service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.boilerplate.server.dao.UserOrderItemMapper;
import com.boilerplate.server.dao.UserOrderMapper;
import com.boilerplate.server.entity.order.OrderVo;
import com.boilerplate.server.model.UserOrder;
import com.boilerplate.server.model.UserOrderItem;
import com.boilerplate.server.sharding.RandomUtils;
import com.boilerplate.server.sharding.ShardingUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@DS(ShardingUtils.SHARDING_DATA_SOURCE_NAME)
public class TestOrderService {
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private UserOrderItemMapper userOrderItemMapper;
    @Autowired
    private RandomUtils randomUtils;
    @Autowired
    private CustomQueryService customQueryService;

    /**
     * 创建测试订单
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderVo createOrder() {
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
        //100-200随机id
        order.setCustomerCity(RandomUtil.randomInt(100, 200));
        order.setCustomerAddress(randomUtils.getAddress());
        //100-300随机金额保留2位小数
        order.setOrderPrice(new BigDecimal(String.format("%.2f", Math.random()*100+200)));
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userOrderMapper.insertSelective(order);

        //订单商品表
        List<UserOrderItem> userOrderItems = new ArrayList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        int itemsNum = RandomUtil.randomInt(1, 4);
        long itemId;
        for (int i = 0; i < itemsNum; i++) {
            itemId = snowflake.nextId();
            UserOrderItem orderItem = new UserOrderItem();
            orderItem.setItemId(itemId);
            orderItem.setItemName(randomUtils.getGoods());
            orderItem.setItemPrice(new BigDecimal(String.format("%.2f", Math.random()*10+80)));
            orderItem.setOrderId(orderId);
            orderItem.setShopId(shopId);
            orderItem.setCustomerId(userId);
            orderItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
            userOrderItemMapper.insertSelective(orderItem);
            userOrderItems.add(orderItem);
        }

        //返回数据
        String subUserId = StringUtils.right(String.valueOf(userId), 5);
        OrderVo orderVo = new OrderVo();
        orderVo.shardingDataBase = Integer.parseInt(subUserId) % 4;
        orderVo.shardingTable = Integer.parseInt(subUserId) % 16;
        orderVo.orderData = order;
        orderVo.orderItems = userOrderItems;
        return orderVo;
    }

    /**
     * 根据订单号查询订单
     * @param orderId
     * @return
     */
    public OrderVo queryOrder(Long orderId) {
        //查询订单表
        UserOrder order = customQueryService.getOrderById(orderId);
        if (order == null) {
            return null;
        }
        Long userId = order.getCustomerId();
        //订单商品
        List<UserOrderItem> userOrderItems = customQueryService.getOrderItems(orderId);

        //返回数据
        String subUserId = StringUtils.right(String.valueOf(userId), 5);
        OrderVo orderVo = new OrderVo();
        orderVo.shardingDataBase = Integer.parseInt(subUserId) % 4;
        orderVo.shardingTable = Integer.parseInt(subUserId) % 16;
        orderVo.orderData = order;
        orderVo.orderItems = userOrderItems;
        return orderVo;
    }

    /**
     * 获取用户订单列表
     * @param customerId
     * @return
     */
    public List<UserOrder> getOrderList(Long customerId, String startTime, String endTime) {
        Map<String, String> params = new HashMap<>();
        params.put("customerId", String.valueOf(customerId));
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        List<UserOrder> orderList = customQueryService.getOrderList(params);
        return orderList;
    }
}
