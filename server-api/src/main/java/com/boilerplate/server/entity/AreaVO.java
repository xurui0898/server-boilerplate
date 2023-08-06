package com.boilerplate.server.entity;

import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

@Data
public class AreaVO {
    /**
     * 自增ID
     * 表字段 : area.id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 名称
     * 表字段 : area.name
     * @mbggenerated
     */
    private String name;

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
    private Integer createdAt;

    /**
     * 转换时间戳为日期时间
     * @return
     */
    public String getCreatedAt() {
        String format = "yyyy-MM-dd HH:mm:ss";
        return DateFormatUtils.format(createdAt * 1000L, format);
    }
}