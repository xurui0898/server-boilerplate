package com.boilerplate.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boilerplate.server.entity.ApiList;
import com.boilerplate.server.mapper.AreaMapper;
import com.boilerplate.server.mapper.AreaStreetMapper;
import com.boilerplate.server.model.Area;
import com.boilerplate.server.model.AreaStreet;
import com.boilerplate.server.service.AreaStreetService;
import com.boilerplate.server.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  区域街道-服务实现类
 * </p>
 *
 * @author 流浪
 * @since 2023-09-05
 */
@Service
public class AreaStreetServiceImpl extends ServiceImpl<AreaStreetMapper, AreaStreet> implements AreaStreetService {
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private AreaStreetMapper streetMapper;

    @Override
    public ApiList<Area> getAreaList(Integer parentId, Integer page, Integer pageSize) {
        LambdaQueryWrapper<Area> query = Wrappers.lambdaQuery();
        query.eq(Area::getParentId, parentId)
                .eq(Area::getStatus,1)
                .last(CommonUtils.getLimitSql(page, pageSize,true));
        List<Area> list = areaMapper.selectList(query);
        //是否存在下一页
        boolean hasNext = list.size() > pageSize;
        if (hasNext) {
            list = list.subList(0, pageSize);
        }
        return ApiList.makeResult(hasNext, list);
    }

    @Override
    public ApiList<AreaStreet> getStreetList(Integer parentCode, Integer page, Integer pageSize) {
        LambdaQueryWrapper<AreaStreet> query = Wrappers.lambdaQuery();
        query.eq(AreaStreet::getParentCode, parentCode)
                .eq(AreaStreet::getStatus,1)
                .last(CommonUtils.getLimitSql(page, pageSize,true));
        List<AreaStreet> list = streetMapper.selectList(query);
        //是否存在下一页
        boolean hasNext = list.size() > pageSize;
        if (hasNext) {
            list = list.subList(0, pageSize);
        }
        return ApiList.makeResult(hasNext, list);
    }
}
