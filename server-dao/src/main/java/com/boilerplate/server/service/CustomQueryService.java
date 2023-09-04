package com.boilerplate.server.service;

import com.boilerplate.server.model.TestUser;
import com.boilerplate.server.model.UserOrder;
import com.boilerplate.server.model.UserOrderItem;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 自定义SQL查询
 */
@Repository
public class CustomQueryService {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private final static String CUSTOM_DAO_SPACE = "com.boilerplate.server.mapper.CustomMapper.";

    /**
     * 自定义SQL查询 统计用户数量
     * @param params
     * @return
     */
    public int countTestUser (Map<String, Short> params) {
        return sqlSessionTemplate.selectOne(CUSTOM_DAO_SPACE + "countTestUser", params);
    }

    /**
     * 自定义SQL查询 批量新增用户
     * @param userList
     * @return
     */
    public int insertTestUserBatch (List<TestUser> userList) {
        return sqlSessionTemplate.insert(CUSTOM_DAO_SPACE + "insertTestUserBatch", userList);
    }

    /**
     * 自定义SQL查询 根据订单号查询订单
     * @param orderId
     * @return
     */
    public UserOrder getOrderById(Long orderId) {
        return sqlSessionTemplate.selectOne(CUSTOM_DAO_SPACE + "getOrderByOrderId", orderId);
    }

    /**
     * 自定义SQL查询 根据用户ID查询订单列表
     * @param params
     * @return
     */
    public List<UserOrder> getOrderList(Map<String,String> params) {
        return sqlSessionTemplate.selectList(CUSTOM_DAO_SPACE + "getOrderListByCustomerId", params);
    }

    /**
     * 自定义SQL查询 根据订单号查询订单商品
     * @param orderId
     * @return
     */
    public List<UserOrderItem> getOrderItems(Long orderId) {
        return sqlSessionTemplate.selectList(CUSTOM_DAO_SPACE + "getOrderItemsByOrderId", orderId);
    }
}
