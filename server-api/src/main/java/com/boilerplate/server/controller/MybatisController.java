package com.boilerplate.server.controller;

import cn.hutool.core.bean.BeanUtil;
import com.boilerplate.server.Service.AreaService;
import com.boilerplate.server.Service.TestUserService;
import com.boilerplate.server.entity.*;
import com.boilerplate.server.entity.area.AreaVO;
import com.boilerplate.server.entity.testuser.TestUserVo;
import com.boilerplate.server.model.Area;
import com.boilerplate.server.model.TestUser;
import com.boilerplate.server.service.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    public ApiResult<TestUserVo> addUser(@Valid @RequestBody AddUserDTO addUserDTO) {
        try {
            //新增实体数据
            TestUser testUser = new TestUser();
            BeanUtil.copyProperties(addUserDTO,testUser);
            testUserService.addUser(testUser);
            //返回结果
            TestUserVo testUserVo = new TestUserVo();
            BeanUtil.copyProperties(testUser,testUserVo);

            return Response.makeOKRsp(testUserVo);
        } catch (Exception e) {
            return Response.makeErrRsp(e.getMessage());
        }
    }

    @PostMapping("addnewuser")
    public ApiResult<TestUserVo> addNewUser(String username, Short sex, Short cityId, String mobile) {
        try {
            //参数实体
            AddUserDTO addUserDTO = new AddUserDTO();
            addUserDTO.setUsername(username);
            addUserDTO.setSex(sex);
            addUserDTO.setCityId(cityId);
            addUserDTO.setMobile(mobile);
            ValidatorUtils.validate(addUserDTO);

            //新增实体数据
            TestUser testUser = new TestUser();
            BeanUtil.copyProperties(addUserDTO,testUser);
            testUserService.addUser(testUser);

            //返回结果
            TestUserVo testUserVo = new TestUserVo();
            BeanUtil.copyProperties(testUser,testUserVo);

            return Response.makeOKRsp(testUserVo);
        } catch (Exception e) {
            return Response.makeErrRsp(e.getMessage());
        }
    }

    @PostMapping("updateuser")
    public ApiResult<TestUserVo> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO) {
        try {
            //更新实体数据
            TestUser testUser = new TestUser();
            BeanUtil.copyProperties(updateUserDTO,testUser);
            testUserService.updateUser(testUser);
            //返回结果
            TestUserVo testUserVo = new TestUserVo();
            BeanUtil.copyProperties(testUser,testUserVo);

            return Response.makeOKRsp(testUserVo);
        } catch (Exception e) {
            return Response.makeErrRsp(e.getMessage());
        }
    }

    @PostMapping("countuser")
    public ApiResult<Integer> countUser(Short sex, Short cityId) {
        try {
            int total = testUserService.countUser(sex, cityId);

            return Response.makeOKRsp(total);
        } catch (Exception e) {
            return Response.makeErrRsp(e.getMessage());
        }
    }

    @PostMapping("adduserbatch")
    public ApiResult<String> addUserBatch(@Valid @RequestBody AddUserDTO addUserDTO) {
        try {
            long start = System.currentTimeMillis();
            List<TestUser> userList = new ArrayList<>();
            for(int i = 1 ;i <= 100; i++) {
                TestUser testUser = new TestUser();
                testUser.setUsername(addUserDTO.getUsername()+i);
                testUser.setSex(addUserDTO.getSex());
                testUser.setCityId(addUserDTO.getCityId());
                testUser.setMobile(addUserDTO.getMobile());
                testUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userList.add(testUser);
            }
            testUserService.insertTestUserBatch(userList);
            long end = System.currentTimeMillis();
            String msg = "100条数据总耗时：" + (end - start) + "ms";
            return Response.makeOKRsp(msg);
        } catch (Exception e) {
            return Response.makeErrRsp(e.getMessage());
        }
    }
}
