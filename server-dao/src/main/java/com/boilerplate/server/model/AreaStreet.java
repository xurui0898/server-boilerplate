package com.boilerplate.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 流浪
 * @since 2023-09-04
 */
@Data
@TableName("area_street")
public class AreaStreet implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 区域编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否街道标记
     */
    private Integer isStreet;

    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Integer createdAt;


}
