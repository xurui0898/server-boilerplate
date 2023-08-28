package com.boilerplate.server.entity.order;

import com.boilerplate.server.model.UserOrder;
import com.boilerplate.server.model.UserOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderVo {
    public Integer shardingDataBase;
    public Integer shardingTable;
    public UserOrder orderData;
    public List<UserOrderItem> orderItems;
}
