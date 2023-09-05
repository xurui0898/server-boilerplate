package com.boilerplate.server.entity.testuser;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @NotEmpty 校验集合
 * @NotBlank 校验String类型(不能校验Integer)
 * @NotNull 校验基本类型
 */
@Data
public class UpdateUserDTO {
    @NotNull(message = "用户ID不能为空")
    private Integer id;

    @Length(min=2, max=8, message="姓名长度为2到8位")
    private String username;

    @Min(value=0, message = "性别只能为0或1")
    @Max(value = 1, message = "性别只能为0或1")
    private Short sex;

    @Min(value=101, message = "城市ID范围限制：101-200")
    @Max(value = 200, message = "城市ID范围限制：101-200")
    private Short cityId;

    private String mobile;
}
