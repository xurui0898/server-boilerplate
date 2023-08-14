package com.boilerplate.server.controller;

import cn.hutool.core.bean.BeanUtil;
import com.boilerplate.server.Service.AreaService;
import com.boilerplate.server.Service.TestUserService;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.boilerplate.server.entity.ApiList;
import com.boilerplate.server.entity.area.AreaVO;
import com.boilerplate.server.entity.testuser.TestUserVo;
import com.boilerplate.server.model.Area;
import com.boilerplate.server.model.TestUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
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
    @Autowired
    private TestUserService testUserService;

    @RequestMapping("arealist")
    public ApiResult<ApiList<AreaVO>> areaList(Integer parentId, Integer page, Integer pageSize) {
        parentId = Optional.ofNullable(parentId).orElse(0);
        page     = Optional.ofNullable(page).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(10);
        if (page < 1) {
            page = 1;
        }

        //根据父ID获取区域列表
        ApiList<Area> areaData = areaService.getList(parentId,page,pageSize);
        //拷贝list，只返回AreaVO实体字段用于展示，还可用Orika进行深拷贝
        List<AreaVO> listView = BeanUtil.copyToList(areaData.getList(), AreaVO.class);

        //组装返回结构
        ApiList<AreaVO> apiList = new ApiList<>(areaData.getHasNext(), listView);

        return Response.makeOKRsp(apiList);
    }

    @RequestMapping("userlist")
    public ApiResult<ApiList<TestUserVo>> userList(Short cityID, Integer page, Integer pageSize) {
        cityID = Optional.ofNullable(cityID).orElse((short) 0);
        page     = Optional.ofNullable(page).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(10);
        if (page < 1) {
            page = 1;
        }

        //根据城市ID查询用户
        ApiList<TestUser> userData = testUserService.getListByCity(cityID,page,pageSize);
        //拷贝list，只返回AreaVO实体字段用于展示，还可用Orika进行深拷贝
        List<TestUserVo> listView = BeanUtil.copyToList(userData.getList(), TestUserVo.class);
        //组装返回结构
        ApiList<TestUserVo> apiList = new ApiList<>(userData.getHasNext(), listView);

        return Response.makeOKRsp(apiList);
    }

    @PostMapping("adduser")
    public ApiResult<TestUserVo> addUser(String username, Short sex, Short cityID, String mobile) {
        //新增实体数据
        TestUser testUser = new TestUser();
        testUser.setUsername(username);
        testUser.setSex(sex);
        testUser.setCityId(cityID);
        testUser.setMobile(mobile);
        testUserService.addUser(testUser);
        //返回结果
        TestUserVo testUserVo = new TestUserVo();
        BeanUtil.copyProperties(testUser,testUserVo);

        return Response.makeOKRsp(testUserVo);
    }
}
