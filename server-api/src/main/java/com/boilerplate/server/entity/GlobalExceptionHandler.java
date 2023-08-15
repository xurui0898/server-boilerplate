package com.boilerplate.server.entity;

import com.boilerplate.server.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

//声明为异常处理器
@SuppressWarnings("all")
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //进行具体异常分类处理
    @ExceptionHandler(Exception.class)
    public ApiResult exceptionHandle(Exception exception) {
        //判断异常类型
        if (exception instanceof BindException) {
            //拦截的是validator绑定的异常
            BindException bindException = (BindException) exception;
            //记录所有错误提示
            List<FieldError> fieldErrors = bindException.getBindingResult().getFieldErrors();
            List<String> errorMsg = new ArrayList<>();
            for (FieldError error : fieldErrors) {
                errorMsg.add(error.getDefaultMessage());
            }
            log.info("参数校验异常="+errorMsg.toString());
            return Response.makeErrRsp(ResultCode.VALID, errorMsg.get(0));
        }
        //不属于特定异常，就返回系统异常错误
        log.error("系统异常="+exception.toString());
        return Response.makeErrRsp(ResultCode.EXCEPTION);
    }
}

