package com.boilerplate.server.utils;

import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.enums.ResultCode;

/**
 * 统一封装：API返回结果
 */
public class Response {
    //返回成功
    public static <T> ApiResult<T> makeOKRsp(T data) {
        return ApiResult.makeResult(ResultCode.SUCCESS, data);
    }

    //返回失败
    public static <T> ApiResult<T> makeErrRsp(String message) {
        return ApiResult.makeResult(ResultCode.FAIL, message);
    }

    //自定义类型
    public static <T> ApiResult<T> makeErrRsp(ResultCode resultCode, String message) {
        return ApiResult.makeResult(resultCode, message);
    }
}
