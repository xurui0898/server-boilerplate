package com.boilerplate.server.Service;

import com.boilerplate.server.dao.AreaMapper;
import com.boilerplate.server.entity.ApiList;
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
    public ApiList<Area> getList(Integer parentId, Integer page, Integer pageSize){
        AreaExample areaExample = new AreaExample();
        AreaExample.Criteria criteria = areaExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        criteria.andStatusEqualTo(true);
        //排序
        areaExample.setOrderByClause("id asc");
        //分页 pageSize+1多查一条记录便于判断是否有下一页
        areaExample.setLimitStart((page-1)*pageSize);
        areaExample.setLimitEnd(pageSize+1);
        List<Area> list = areaMapper.selectByExample(areaExample);

        //是否存在下一页
        boolean hasNext = list.size() > pageSize;
        if (hasNext) {
            list = list.subList(0, pageSize);
        }

        return new ApiList<>(hasNext, list);
    }
}
