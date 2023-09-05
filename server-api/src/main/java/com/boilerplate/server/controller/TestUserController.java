package com.boilerplate.server.controller;

import cn.hutool.core.bean.BeanUtil;
import com.boilerplate.server.entity.ApiList;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.testuser.AddUserDTO;
import com.boilerplate.server.entity.testuser.TestUserVo;
import com.boilerplate.server.entity.testuser.UpdateUserDTO;
import com.boilerplate.server.model.MpTestUser;
import com.boilerplate.server.model.TestUser;
import com.boilerplate.server.service.TestUserService;
import com.boilerplate.server.utils.Response;
import com.boilerplate.server.utils.ValidatorUtils;
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
 * TestUser接口测试
 * Mybatis框架，自定义SQL方法：addUserBatch，countUser
 */
@RestController
@Slf4j
@RequestMapping("test-user")
public class TestUserController {
    @Autowired
    private TestUserService testUserService;

    @RequestMapping("userList")
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
        ApiList<TestUserVo> apiList = ApiList.makeResult(userData.getHasNext(), listView);
        return Response.makeOKRsp(apiList);
    }

    @RequestMapping("mpUserList")
    public ApiResult<ApiList<MpTestUser>> mpUserList(Short cityID, Integer page, Integer pageSize) {
        cityID = Optional.ofNullable(cityID).orElse((short) 0);
        page     = Optional.ofNullable(page).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(10);
        if (page < 1) {
            page = 1;
        }

        //根据城市ID查询用户，Mybatis-Plus框架
        ApiList<MpTestUser> userData = testUserService.getMpListByCity(cityID,page,pageSize);
        return Response.makeOKRsp(userData);
    }

    @PostMapping("addUser")
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

    @PostMapping("addUserBatch")
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
            testUserService.addUserBatch(userList);
            long end = System.currentTimeMillis();
            String msg = "100条数据总耗时：" + (end - start) + "ms";
            return Response.makeOKRsp(msg);
        } catch (Exception e) {
            return Response.makeErrRsp(e.getMessage());
        }
    }

    @PostMapping("addNewUser")
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

    @PostMapping("updateUser")
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

    @PostMapping("countUser")
    public ApiResult<Integer> countUser(Short sex, Short cityId) {
        try {
            int total = testUserService.countUser(sex, cityId);

            return Response.makeOKRsp(total);
        } catch (Exception e) {
            return Response.makeErrRsp(e.getMessage());
        }
    }
}
