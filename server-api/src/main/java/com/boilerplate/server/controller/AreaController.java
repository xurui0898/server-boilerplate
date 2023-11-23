package com.boilerplate.server.controller;

import com.boilerplate.server.entity.ApiList;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.area.AreaVO;
import com.boilerplate.server.model.Area;
import com.boilerplate.server.model.AreaStreet;
import com.boilerplate.server.service.AreaStreetService;
import com.boilerplate.server.test.ConvertMapper;
import com.boilerplate.server.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Area接口测试
 * Mybatis-Plus框架
 */
@RestController
@Slf4j
@RequestMapping("area")
public class AreaController {
    @Autowired
    private AreaStreetService areaStreetService;
    @Autowired
    private ConvertMapper convertMapper;

    @RequestMapping("areaList")
    public ApiResult<ApiList<AreaVO>> areaList(Integer parentId, Integer page, Integer pageSize) {
        parentId = Optional.ofNullable(parentId).orElse(0);
        page     = Optional.ofNullable(page).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(10);
        if (page < 1) {
            page = 1;
        }

        //根据父ID获取区域列表
        ApiList<Area> areaData = areaStreetService.getAreaList(parentId,page,pageSize);
        //拷贝list，只返回AreaVO实体字段用于展示，还可用Orika进行深拷贝
        //List<AreaVO> listView = BeanUtil.copyToList(areaData.getList(), AreaVO.class);
        List<AreaVO> listView = convertMapper.area2AreaVO(areaData.getList());

        //组装返回结构
        ApiList<AreaVO> apiList = ApiList.makeResult(areaData.getHasNext(), listView);
        return Response.makeOKRsp(apiList);
    }

    @RequestMapping("streetList")
    public ApiResult<ApiList<AreaStreet>> streetList(Integer parentCode, Integer page, Integer pageSize) {
        parentCode = Optional.ofNullable(parentCode).orElse(0);
        page       = Optional.ofNullable(page).orElse(1);
        pageSize   = Optional.ofNullable(pageSize).orElse(10);
        if (page < 1) {
            page = 1;
        }

        //组装返回结构
        ApiList<AreaStreet> apiList = areaStreetService.getStreetList(parentCode,page,pageSize);
        return Response.makeOKRsp(apiList);
    }
}
