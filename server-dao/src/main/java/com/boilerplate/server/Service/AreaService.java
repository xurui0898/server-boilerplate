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

        return areaMapper.selectByExample(areaExample);
    }
}
