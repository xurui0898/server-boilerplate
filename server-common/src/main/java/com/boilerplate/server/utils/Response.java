package com.boilerplate.server.utils;

import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.enums.ResultCodeEnum;

/**
 * 统一封装：API返回结果
 */
public class Response {
    //返回成功
    public static <T> ApiResult<T> makeOKRsp(T data) {
        return ApiResult.makeResult(ResultCodeEnum.SUCCESS, data);
    }

    //返回失败
    public static <T> ApiResult<T> makeErrRsp(String message) {
        return ApiResult.makeResult(ResultCodeEnum.FAIL, message);
    }

    //自定义类型
    public static <T> ApiResult<T> makeErrRsp(ResultCodeEnum resultCode, String message) {
        return ApiResult.makeResult(resultCode, message);
    }
}
