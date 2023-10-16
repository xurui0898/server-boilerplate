package com.boilerplate.server.entity;

import com.boilerplate.server.enums.ResultCodeEnum;
import lombok.Data;

/**
 * API返回结果格式
 */
@Data
public class ApiResult<T> {
    private Integer code;//返回状态码
    private String msg;  //返回描述信息
    private T data;      //返回数据

    public static <T> ApiResult<T> makeResult(ResultCodeEnum code, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.code = code.getCode();
        result.msg = code.getMessage();
        result.data = data;
        return result;
    }

    public static <T> ApiResult<T> makeResult(ResultCodeEnum code, String msg) {
        ApiResult<T> result = new ApiResult<>();
        result.code = code.getCode();
        result.msg = msg;
        return result;
    }

    public static <T> ApiResult<T> makeResult(ResultCodeEnum code, String msg, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.code = code.getCode();
        result.msg = msg;
        result.data = data;
        return result;
    }
}
