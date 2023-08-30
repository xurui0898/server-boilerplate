package com.boilerplate.server.init;

import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.boilerplate.server.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@SuppressWarnings("all")
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //进行具体异常分类处理
    @ExceptionHandler(Exception.class)
    public ApiResult exceptionHandle(Exception exception) {
        String validMsg = null;
        //判断异常类型
        if (exception instanceof BindException) {
            //拦截的是validator绑定的异常
            BindException bindException = (BindException) exception;
            List<FieldError> fieldErrors = bindException.getBindingResult().getFieldErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : fieldErrors) {
                stringBuilder.append(stringBuilder.length()>0?",":"").append(fieldError.getDefaultMessage());
            }
            validMsg = String.format("%s:%s", ResultCode.VALID.getMsg(), stringBuilder);
        }else if (exception instanceof MethodArgumentNotValidException){
            BindingResult result = ((MethodArgumentNotValidException) exception).getBindingResult();
            if (result.hasErrors()){
                List<ObjectError> errors = result.getAllErrors();
                StringBuilder stringBuilder = new StringBuilder();
                errors.forEach(error -> {
                    FieldError fieldError = (FieldError) error;
                    stringBuilder.append(stringBuilder.length()>0?",":"").append(fieldError.getDefaultMessage());
                });
                validMsg = String.format("%s:%s", ResultCode.VALID.getMsg(), stringBuilder);
            }
        } else if (exception instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> items = ((ConstraintViolationException) exception).getConstraintViolations();
            StringBuilder stringBuilder = new StringBuilder();
            items.forEach( item -> stringBuilder.append(stringBuilder.length()>0?",":"").append(item.getMessage()));
            validMsg = String.format("%s:%s", ResultCode.VALID.getMsg(), stringBuilder);
        }

        //返回结果
        if (validMsg != null) {
            log.info("参数校验异常="+validMsg);
            return Response.makeErrRsp(ResultCode.VALID, validMsg);
        }else {
            //未处理类型，返回系统异常
            log.error("系统异常="+exception.toString());
            return Response.makeErrRsp(ResultCode.EXCEPTION, exception.getMessage());
        }
    }
}

