package com.boilerplate.server.sharding;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.boilerplate.server.dao.OrderMapper;
import com.boilerplate.server.model.TEntOrder;
import com.boilerplate.server.model.TEntOrderDetail;
import com.boilerplate.server.model.TEntOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
@DS(ShardingConstants.SHARDING_DATA_SOURCE_NAME)
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisIdGeneratorService redisIdGeneratorService;

    public TEntOrder getOrderById(Long id) {
        return orderMapper.getOrderById(id);
    }

    public Map<String, Object> queryOrder(Long orderId) {
        return orderMapper.queryOrder(orderId);
    }

    @Transactional
    public void save() {
        Long entId = 12L;
        String regionCode = "ZJ";

        //保存订单基本信息
        TEntOrder tEntOrder = new TEntOrder();
        Long orderId = redisIdGeneratorService.createUniqueId(String.valueOf(entId));
        tEntOrder.setId(orderId);
        tEntOrder.setRegionCode(regionCode);
        tEntOrder.setAmount(new BigDecimal(19.0));
        tEntOrder.setMobile("150****1899");
        tEntOrder.setEntId(entId);
        orderMapper.saveOrder(tEntOrder);

        //保存订单详情
        TEntOrderDetail tEntOrderDetail = new TEntOrderDetail();
        Long detailId = redisIdGeneratorService.createUniqueId(String.valueOf(entId));
        tEntOrderDetail.setId(detailId);
        tEntOrderDetail.setAddress("浙江省杭州市阿里巴巴");
        tEntOrderDetail.setOrderId(orderId);
        tEntOrderDetail.setEntId(entId);
        tEntOrderDetail.setStatus(1);
        tEntOrderDetail.setRegionCode(regionCode);
        orderMapper.saveOrderDetail(tEntOrderDetail);

        //保存订单条目表
        {
            //保存条目 1
            TEntOrderItem item1 = new TEntOrderItem();
            Long itemId = redisIdGeneratorService.createUniqueId(String.valueOf(entId));
            item1.setId(itemId);
            item1.setEntId(entId);
            item1.setOrderId(orderId);
            item1.setRegionCode("BG");
            item1.setGoodId("aaaaaaaaaaaa");
            item1.setGoodName("我的商品111111");
            orderMapper.saveOrderItem(item1);
            //保存条目 2
            TEntOrderItem item2 = new TEntOrderItem();
            Long itemId2 = redisIdGeneratorService.createUniqueId(String.valueOf(entId));
            item2.setId(itemId2);
            item2.setEntId(entId);
            item2.setRegionCode("BJ");
            item2.setOrderId(orderId);
            item2.setGoodId("bbbbbbbbbbbb");
            item2.setGoodName("我的商品22222");
            orderMapper.saveOrderItem(item2);
        }
    }

}
