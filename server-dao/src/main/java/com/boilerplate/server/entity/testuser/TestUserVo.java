package com.boilerplate.server.entity.testuser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;

/**
 * @JsonInclude(JsonInclude.Include.NON_NULL)
 * 在实体类上加入此注解，接口返回值可以剔除掉不需要的null属性
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestUserVo {
    /**
     *
     * 表字段 : test_user.id
     * @mbggenerated
     */
    private Integer id;

    /**
     *
     * 表字段 : test_user.username
     * @mbggenerated
     */
    private String username;

    /**
     *
     * 表字段 : test_user.sex
     * @mbggenerated
     */
    private Short sex;

    /**
     *
     * 表字段 : test_user.city_id
     * @mbggenerated
     */
    private Short cityId;

    /**
     *
     * 表字段 : test_user.mobile
     * @mbggenerated
     */
    private String mobile;

    /**
     *
     * 表字段 : test_user.create_time
     * @mbggenerated
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

}
