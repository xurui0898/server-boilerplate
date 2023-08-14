package com.boilerplate.server.entity.testuser;

import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;

@Data
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
    private Timestamp createTime;
    //返回格式化日期时间
    public String getCreateTime() {
        if (createTime == null) {
            return "";
        }
        String format = "yyyy-MM-dd HH:mm:ss";
        return DateFormatUtils.format(createTime, format);
    }

}
