package com.boilerplate.server.enums;

import lombok.Getter;

/**
 * 响应枚举状态码
 */
public enum ResultCodeEnum {
    //成功
    SUCCESS(200,"成功"),
    //失败
    FAIL(201,"失败"),
    //参数错误
    VALID(202, "参数错误"),
    //网络异常
    EXCEPTION(203,"系统异常"),
    //重复请求
    DUPLICATE(204,"重复请求"),
    //未认证
    UNAUTHORIZED(401,"签名错误");

    @Getter
    private final Integer code;
    @Getter
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessageByCode(Integer code) {
        for (ResultCodeEnum codeEnum : values()) {
            if (codeEnum.getCode().equals(code)) {
                return codeEnum.getMessage();
            }
        }
        return null;
    }
}
