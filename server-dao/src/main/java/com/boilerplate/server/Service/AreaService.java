package com.boilerplate.server.Service;

import com.boilerplate.server.dao.AreaMapper;
import com.boilerplate.server.model.Area;
import com.boilerplate.server.model.AreaExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {
    @Autowired
    private AreaMapper areaMapper;

    /**
     * 根据父级ID获取列表
     * @param parentId
     * @return
     */
    public List<Area> getList(Integer parentId){
        AreaExample areaExample = new AreaExample();
        AreaExample.Criteria criteria = areaExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        criteria.andStatusEqualTo(true);
        //排序
        areaExample.setOrderByClause("id asc");

        return areaMapper.selectByExample(areaExample);
    }

    /**
     * 根据父级ID获取列表 分页查询
     * @param parentId
     * @return
     */
    public List<Area> getList(Integer parentId, Integer page, Integer pageSize){
        AreaExample areaExample = new AreaExample();
        AreaExample.Criteria criteria = areaExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        criteria.andStatusEqualTo(true);
        //排序
        areaExample.setOrderByClause("id asc");
        //分页
        areaExample.setLimitStart((page-1)*pageSize);
        areaExample.setLimitEnd(pageSize);

        return areaMapper.selectByExample(areaExample);
    }
}
