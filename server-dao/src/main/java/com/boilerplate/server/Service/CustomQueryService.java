package com.boilerplate.server.Service;

import com.boilerplate.server.model.TestUser;
import com.boilerplate.server.model.UserOrder;
import com.boilerplate.server.model.UserOrderItem;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义SQL查询
 */
@Repository
public class CustomQueryService {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private final static String CUSTOM_DAO_SPACE = "com.boilerplate.server.dao.CustomMapper.";

    /**
     * 自定义SQL查询 统计用户数量
     * @param sex
     * @param cityId
     * @return
     */
    public int countTestUser (Short sex, Short cityId) {
        Map<String, Short> param = new HashMap<>();
        param.put("sex", sex);
        param.put("cityId", cityId);
        return sqlSessionTemplate.selectOne(CUSTOM_DAO_SPACE + "countTestUser", param);
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
    public UserOrder getOrderByOrderId (Long orderId) {
        return sqlSessionTemplate.selectOne(CUSTOM_DAO_SPACE + "getOrderByOrderId", orderId);
    }

    /**
     * 自定义SQL查询 根据订单号查询订单商品
     * @param orderId
     * @return
     */
    public List<UserOrderItem> getOrderItemsByOrderId (Long orderId) {
        return sqlSessionTemplate.selectList(CUSTOM_DAO_SPACE + "getOrderItemsByOrderId", orderId);
    }
}
