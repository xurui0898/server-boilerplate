package com.boilerplate.server.controller;

import cn.hutool.core.bean.BeanUtil;
import com.boilerplate.server.Service.AreaService;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.AreaVO;
import com.boilerplate.server.entity.Response;
import com.boilerplate.server.model.Area;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * mybatis测试
 */
@RestController
@Slf4j
@RequestMapping("mybatis")
public class MybatisController {
    @Autowired
    private AreaService areaService;

    @RequestMapping("arealist")
    public ApiResult<List<AreaVO>> areaList() {
        //根据父ID获取区域列表
        List<Area> listData = areaService.getList(2);
        //处理展示实体字段，还可用Orika进行深拷贝
        List<AreaVO> listView = BeanUtil.copyToList(listData, AreaVO.class);

        ApiResult<List<AreaVO>> apiResult = Response.makeOKRsp(listView);
        return apiResult;
    }
}
