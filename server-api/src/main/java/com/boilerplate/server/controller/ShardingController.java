package com.boilerplate.server.controller;

import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.boilerplate.server.sharding.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Sharding-jdbc 分库分表测试
 */
@RestController
@Slf4j
@RequestMapping("sharding")
public class ShardingController {
    @Autowired
    private OrderService orderService;

    @GetMapping("query")
    public ApiResult<Map<String, Object>> queryOrder(String orderId) {
        try {
            Map<String, Object> orderMap = orderService.queryOrder(Long.valueOf(orderId));
            return Response.makeOKRsp(orderMap);
        } catch (Exception e) {
            return Response.makeErrRsp(e.getMessage());
        }
    }

    @GetMapping("save")
    public ApiResult<String> save() {
        try {
            orderService.save();
            return Response.makeOKRsp("SUCCESS");
        } catch (Exception e) {
            return Response.makeErrRsp(e.getMessage());
        }
    }
}
