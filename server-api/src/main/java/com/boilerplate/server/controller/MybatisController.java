package com.boilerplate.server.controller;

import cn.hutool.core.bean.BeanUtil;
import com.boilerplate.server.Service.AreaService;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.boilerplate.server.entity.area.AreaList;
import com.boilerplate.server.entity.area.AreaVO;
import com.boilerplate.server.model.Area;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public ApiResult<AreaList<AreaVO>> areaList(Integer parentId, Integer page, Integer pageSize) {
        parentId = Optional.ofNullable(parentId).orElse(0);
        page     = Optional.ofNullable(page).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(10);
        if (page < 1) {
            page = 1;
        }

        //根据父ID获取区域列表
        List<Area> listData = areaService.getList(parentId,page,pageSize);
        //拷贝list只返回部分字段，还可用Orika进行深拷贝
        List<AreaVO> listView = BeanUtil.copyToList(listData, AreaVO.class);

        //组装返回结构
        AreaList<AreaVO> areaList = new AreaList<>(false, listView);

        return Response.makeOKRsp(areaList);
    }
}
