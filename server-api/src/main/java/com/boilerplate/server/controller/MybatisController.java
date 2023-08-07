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
        AreaList<Area> areaData = areaService.getList(parentId,page,pageSize);
        //拷贝list，只返回AreaVO实体字段用于展示，还可用Orika进行深拷贝
        List<AreaVO> listView = BeanUtil.copyToList(areaData.getList(), AreaVO.class);

        //组装返回结构
        AreaList<AreaVO> areaList = new AreaList<>(areaData.getHasNext(), listView);

        return Response.makeOKRsp(areaList);
    }
}
