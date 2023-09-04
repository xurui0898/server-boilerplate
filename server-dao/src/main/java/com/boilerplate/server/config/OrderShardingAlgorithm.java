package com.boilerplate.server.config;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 订单复合分片算法
 * 以订单id的后5位(用户ID后5位)来确定路由到那个表中
 * 1、目前处理 = 和 in 操作，其余的操作，比如 >、< 等不支持。
 */
public class OrderShardingAlgorithm implements ComplexKeysShardingAlgorithm<BigDecimal> {
    /**
     * 订单id字段
     */
    private static final String COLUMN_ORDER_ID = "order_id";
    /**
     * 用户id字段
     */
    private static final String COLUMN_CUSTOMER_ID = "customer_id";

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<BigDecimal> shardingValue) {
        if (!shardingValue.getColumnNameAndRangeValuesMap().isEmpty()) {
            throw new RuntimeException("不支持除了=和in的操作");
        }
        // 获取订单id
        Collection<BigDecimal> orderIds = shardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_ORDER_ID, new ArrayList<>(1));
        // 获取用户id
        Collection<BigDecimal> customerIds = shardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_CUSTOMER_ID, new ArrayList<>(1));


        // 整合订单id、用户id
        List<String> ids = new ArrayList<>(16);
        ids.addAll(ids2String(orderIds));
        ids.addAll(ids2String(customerIds));

        return ids.stream()
                // 截取后用户id后5位 做Sharding路由
                .map(id -> id.substring(id.length() - 5))
                // 去重
                .distinct()
                // 转换成int
                .map(Integer::new)
                // 对可用的表名求余数，获取到真实的表的后缀
                .map(idSuffix -> idSuffix % availableTargetNames.size())
                // 转换成string
                .map(String::valueOf)
                // 获取到真实的表
                .map(tableSuffix -> availableTargetNames.stream().filter(targetName -> targetName.endsWith(tableSuffix)).findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 转换成String
     */
    private List<String> ids2String(Collection<?> ids) {
        List<String> result = new ArrayList<>(ids.size());
        ids.forEach(id -> result.add(Objects.toString(id)));
        return result;
    }
}