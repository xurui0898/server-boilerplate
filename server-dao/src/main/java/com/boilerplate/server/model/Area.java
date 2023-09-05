package com.boilerplate.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 区域表
 * </p>
 *
 * @author 流浪
 * @since 2023-09-05
 */
@Data
@TableName("area")
public class Area implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 地区层级
     */
    private Integer depth;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 行政编码
     */
    private String adminCode;

    /**
     * 天猫状态(是否在天猫服务区域内,1-在,0-不在)
     */
    private Integer tmallStatus;

    /**
     * 创建时间
     */
    private Integer createdAt;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 合伙人id
     */
    private String partnerId;

    private String initials;

    /**
     * 区域id，对应region的主键ID
     */
    private Integer regionId;


}
