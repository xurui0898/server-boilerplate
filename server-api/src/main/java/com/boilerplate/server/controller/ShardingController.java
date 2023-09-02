package com.boilerplate.server.controller;

import com.boilerplate.server.Service.TestOrderService;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.boilerplate.server.entity.order.OrderVo;
import com.boilerplate.server.model.UserOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Sharding-jdbc 分库分表测试
 */
@RestController
@Slf4j
@Validated
@RequestMapping("sharding")
public class ShardingController {
    @Autowired
    private TestOrderService testOrderService;

    @GetMapping("createOrder")
    public ApiResult<OrderVo> createOrder() {
        try {
            OrderVo orderVo = testOrderService.createOrder();
            return Response.makeOKRsp(orderVo);
        } catch (Exception e) {
            log.info(e.toString());
            return Response.makeErrRsp(e.getMessage());
        }
    }

    @GetMapping("queryOrder")
    public ApiResult<OrderVo> queryOrder(@Valid @NotNull(message = "订单号不能为空")Long orderId) {
        try {
            OrderVo orderVo = testOrderService.queryOrder(orderId);
            return Response.makeOKRsp(orderVo);
        } catch (Exception e) {
            log.info(e.toString());
            return Response.makeErrRsp(e.getMessage());
        }
    }

    @GetMapping("batchQueryOrder")
    public ApiResult<List<OrderVo>> batchQueryOrder(@RequestBody List<Long> orderIds) {
        try {
            List<OrderVo> orderList = testOrderService.batchQueryOrder(orderIds);
            return Response.makeOKRsp(orderList);
        } catch (Exception e) {
            log.info(e.toString());
            return Response.makeErrRsp(e.getMessage());
        }
    }

    @GetMapping("getOrderList")
    public ApiResult<List<UserOrder>> getOrderList(@Valid @NotNull(message = "用户ID不能为空") Long customerId, String startTime, String endTime) {
        try {
            List<UserOrder> orderList = testOrderService.getOrderList(customerId,startTime,endTime);
            return Response.makeOKRsp(orderList);
        } catch (Exception e) {
            log.info(e.toString());
            return Response.makeErrRsp(e.getMessage());
        }
    }

}
