package com.boilerplate.server.Service;

import com.boilerplate.server.dao.TestUserMapper;
import com.boilerplate.server.entity.ApiList;
import com.boilerplate.server.model.TestUser;
import com.boilerplate.server.model.TestUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TestUserService {
    @Autowired
    private TestUserMapper testUserMapper;
    @Autowired
    private CustomQueryService customQueryService;

    /**
     * 根据城市ID查询用户列表 分页查询
     * @return
     */
    public ApiList<TestUser> getListByCity(Short cityId, Integer page, Integer pageSize){
        TestUserExample testUserExample = new TestUserExample();
        TestUserExample.Criteria criteria = testUserExample.createCriteria();
        criteria.andCityIdEqualTo(cityId);
        //排序
        testUserExample.setOrderByClause("id asc");
        //分页 pageSize+1多查一条记录便于判断是否有下一页
        testUserExample.setLimitStart((page-1)*pageSize);
        testUserExample.setLimitEnd(pageSize+1);
        List<TestUser> list = testUserMapper.selectByExample(testUserExample);

        //是否存在下一页
        boolean hasNext = list.size() > pageSize;
        if (hasNext) {
            list = list.subList(0, pageSize);
        }

        return new ApiList<>(hasNext, list);
    }

    /**
     * 新增用户
     * @param testUser
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int addUser(TestUser testUser) {
        if (testUser.getCreateTime() == null) {
            testUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        return testUserMapper.insertSelective(testUser);
    }

    /**
     * 按ID更新用户
     * @param testUser
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(TestUser testUser){
        return testUserMapper.updateByPrimaryKeySelective(testUser);
    }

    /**
     * 自定义SQL查询 统计用户数量
     * @param sex
     * @param cityId
     * @return
     */
    public int countUser(Short sex, Short cityId){
        return customQueryService.countTestUser(sex, cityId);
    }

    /**
     * 自定义SQL查询 批量新增用户
     * @param userList
     * @return
     */
    public int insertTestUserBatch (List<TestUser> userList) {
        return customQueryService.insertTestUserBatch(userList);
    }
}
