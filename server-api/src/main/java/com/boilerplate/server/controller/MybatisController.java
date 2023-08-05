package com.boilerplate.server.controller;

import com.boilerplate.server.Service.AreaService;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.boilerplate.server.model.Area;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public ApiResult<List<Area>> areaList() {
        List<Area> list = areaService.selectByExample(null);
        ApiResult<List<Area>> apiResult = Response.makeOKRsp(list);

        return apiResult;
    }
}
