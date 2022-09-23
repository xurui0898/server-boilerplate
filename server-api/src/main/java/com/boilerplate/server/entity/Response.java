package com.boilerplate.server.entity;

import com.boilerplate.server.enums.ResultCode;

/**
 * 统一封装：API返回结果
 */
public class Response {
    //返回成功
    public static <T> ResponseResult<T> makeOKRsp(T data) {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setData(data);
    }

    public static <T> ResponseResult<T> makeOKRsp(T data, String message) {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setData(data).setMsg(message);
    }

    //返回失败
    public static <T> ResponseResult<T> makeErrRsp(String message) {
        return new ResponseResult<T>().setCode(ResultCode.FAIL).setMsg(message);
    }

    public static <T> ResponseResult<T> makeErrRsp(String message, T data) {
        return new ResponseResult<T>().setCode(ResultCode.FAIL).setData(data).setMsg(message);
    }

    //自定义类型
    public static <T> ResponseResult<T> makeRsp(ResultCode resultCode) {
        return new ResponseResult<T>().setCode(resultCode);
    }
}
