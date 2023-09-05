package com.boilerplate.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boilerplate.server.entity.ApiList;
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
    ApiList<AreaStreet> getList(Integer parentCode, Integer page, Integer pageSize);
}
