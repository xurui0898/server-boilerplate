package com.boilerplate.server.entity;

import com.boilerplate.server.enums.ResultCode;

/**
 * API返回结果格式
 */
public class ApiResult<T> {
    private Integer code;//返回状态码
    private String msg;  //返回描述信息
    private T data;      //返回数据

    public int getCode() {
        return code;
    }

    public ApiResult<T> setCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        return this;
    }

    public ApiResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ApiResult<T> setData(T data) {
        this.data = data;
        return this;
    }

}
