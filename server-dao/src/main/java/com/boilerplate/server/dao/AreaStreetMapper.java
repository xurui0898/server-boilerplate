package com.boilerplate.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boilerplate.server.model.AreaStreet;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 流浪
 * @since 2023-09-04
 */
public interface AreaStreetMapper extends BaseMapper<AreaStreet> {
    List<AreaStreet> getList(Integer parentCode);
}
