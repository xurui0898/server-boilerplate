package com.boilerplate.server.service;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator Bean对象参数校验工具
 * 校验方式适配 @Valid 和 @Validated 注解的校验规则
 */
@Slf4j
public class ValidatorUtils {

    /** 通过建造者工厂模式创建 javax.validation.Validator 对象*/
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Bean对象的整体校验，有不符合规范的属性，迭代异常 Message 并拼接后全部抛出
     * @param	obj     待验证对象
     * @param	groups  分组
     */
    public static void validate(Object obj, Class<?>... groups) {
        Set<ConstraintViolation<Object>> resultSet = validator.validate(obj, groups);
        if (resultSet.size() > 0) {
            // 如果存在错误结果，则将其解析并进行拼凑后异常抛出
            List<String> errorMsgList = resultSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            /*StringBuilder errorMessage = new StringBuilder();
            errorMsgList.forEach(o -> errorMessage.append(o).append(";"));
            throw new IllegalArgumentException(errorMessage.toString());*/
            //只抛出第一个异常提示
            throw new IllegalArgumentException(errorMsgList.get(0));
        }
    }
}

