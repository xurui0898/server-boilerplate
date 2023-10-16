package com.boilerplate.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.boilerplate.server.annotation.DuplicateCheck;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.order.OrderVo;
import com.boilerplate.server.enums.ResultCodeEnum;
import com.boilerplate.server.redis.RedisUtils;
import com.boilerplate.server.service.TestOrderService;
import com.boilerplate.server.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Redis测试
 */
@RestController
@Slf4j
@Validated
public class RedisController {
    private static final String ORDER_DATA_PREFIX_KEY = "ruicode_order_data:";

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private TestOrderService testOrderService;

    @GetMapping("redis/addOrderData")
    @DuplicateCheck(key = "orderId",expireTime = 10)
    public ApiResult<Boolean> addOrderData(@Valid @NotNull(message = "订单号不能为空")Long orderId) {
        try {
            OrderVo orderVo = testOrderService.queryOrder(orderId);
            if (orderVo == null) {
                throw new Exception("订单号不存在，请核实");
            }
            Map<String, Object> map = BeanUtil.beanToMap(orderVo);
            String key = String.format("%s%s", ORDER_DATA_PREFIX_KEY, orderId);
            Boolean result = redisUtils.hmset(key, map, 86400);

            //返回结果集封装
            ApiResult<Boolean> apiResult = Response.makeOKRsp(result);
            return apiResult;
        } catch (Exception e) {
            return Response.makeErrRsp(ResultCodeEnum.VALID, e.getMessage());
        }
    }

    @RequestMapping("redis/queryOrderData")
    public ApiResult<OrderVo> queryOrderData(@Valid @NotNull(message = "订单号不能为空")Long orderId) {
        try {
            String key = String.format("%s%s", ORDER_DATA_PREFIX_KEY, orderId);
            Map<Object, Object> map = redisUtils.hmget(key);
            if (MapUtil.isEmpty(map)) {
                throw new Exception("未找到该订单数据，请核实");
            }
            String OrderStr = JSONUtil.toJsonStr(map);
            OrderVo orderVo = JSONUtil.toBean(OrderStr, OrderVo.class);

            //返回结果集封装
            return Response.makeOKRsp(orderVo);
        } catch (Exception e) {
            return Response.makeErrRsp(ResultCodeEnum.VALID, e.getMessage());
        }
    }
}
