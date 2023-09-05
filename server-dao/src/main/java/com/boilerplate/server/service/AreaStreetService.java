package com.boilerplate.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boilerplate.server.entity.ApiList;
import com.boilerplate.server.model.Area;
import com.boilerplate.server.model.AreaStreet;

/**
 * <p>
 *  区域街道-服务类
 * </p>
 *
 * @author 流浪
 * @since 2023-09-05
 */
public interface AreaStreetService extends IService<AreaStreet> {
    ApiList<Area> getAreaList(Integer parentId, Integer page, Integer pageSize);

    ApiList<AreaStreet> getStreetList(Integer parentCode, Integer page, Integer pageSize);
}
