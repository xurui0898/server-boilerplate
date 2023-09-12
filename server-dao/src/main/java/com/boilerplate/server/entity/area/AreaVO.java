package com.boilerplate.server.entity.area;

import lombok.Data;

@Data
public class AreaVO {
    /**
     * 自增ID
     * 表字段 : area.id
     * @mbggenerated
     */
    private Integer areaId;

    /**
     * 名称
     * 表字段 : area.name
     * @mbggenerated
     */
    private String areaName;

    /**
     * 父级ID
     * 表字段 : area.parent_id
     * @mbggenerated
     */
    private Integer parentId;

    /**
     * 地区层级
     * 表字段 : area.depth
     * @mbggenerated
     */
    private Byte depth;

    /**
     * 行政编码
     * 表字段 : area.admin_code
     * @mbggenerated
     */
    private String adminCode;

    /**
     * 创建时间
     * 表字段 : area.created_at
     * @mbggenerated
     */
    private String createTime;
}