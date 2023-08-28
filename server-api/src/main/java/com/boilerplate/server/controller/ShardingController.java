package com.boilerplate.server.controller;

import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.boilerplate.server.Service.TestOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sharding-jdbc 分库分表测试
 */
@RestController
@Slf4j
@RequestMapping("sharding")
public class ShardingController {
    @Autowired
    private TestOrderService testOrderService;

    @GetMapping("createorder")
    public ApiResult<Long> createOrder() {
        try {
            Long orderId = testOrderService.createOrder();
            return Response.makeOKRsp(orderId);
        } catch (Exception e) {
            log.info(e.toString());
            return Response.makeErrRsp(e.getMessage());
        }
    }

}
