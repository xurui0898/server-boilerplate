package com.boilerplate.server.entity;

import com.boilerplate.server.enums.ResultCode;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//声明为异常处理器
@SuppressWarnings("all")
@RestControllerAdvice
public class GlobalExceptionHandler {
    //进行具体异常分类处理
    @ExceptionHandler(Exception.class)
    public ApiResult exceptionHandle(Exception exception) {
        //判断异常类型
        if (exception instanceof BindException) {
            //拦截的是validator绑定的异常
            BindException bindException = (BindException) exception;
            //异常信息
            String message = bindException.getBindingResult()
                    .getAllErrors()
                    .get(0)
                    .getDefaultMessage();
            return Response.makeErrRsp(ResultCode.VALID, message);
        }
        //不属于那两个异常 就返回系统异常错误
        return Response.makeErrRsp(ResultCode.EXCEPTION, exception.getMessage());
    }
}

