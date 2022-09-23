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
