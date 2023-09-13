package com.boilerplate.server.test;

import com.boilerplate.server.entity.area.AreaVO;
import com.boilerplate.server.model.Area;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct 实体属性映射工具
 */
@Mapper(componentModel = "spring")
public abstract class ConvertMapper {
    //Area->AreaVO 集合批量转换
    public abstract List<AreaVO> area2AreaVO(List<Area> areaList);

    //Area->AreaVO 对象转换
    @Mapping(source = "id",target = "areaId")
    @Mapping(source = "name",target = "areaName")
    public abstract AreaVO area2AreaVO(Area area);

    @AfterMapping
    public void area2AreaVOAfter(Area area, @MappingTarget AreaVO areaVO){
        if (area.getCreatedAt() != null) {
            String format = "yyyy-MM-dd HH:mm:ss";
            areaVO.setCreateTime(DateFormatUtils.format(area.getCreatedAt()*1000L, format));
        }
    }
}
