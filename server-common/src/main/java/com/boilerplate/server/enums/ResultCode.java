package com.boilerplate.server.enums;

import lombok.Getter;

/**
 * 响应枚举状态码
 */
public enum ResultCode {
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
    private final int code;
    @Getter
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
