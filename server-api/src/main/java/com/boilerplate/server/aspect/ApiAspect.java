package com.boilerplate.server.aspect;

import com.boilerplate.server.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

/**
 * Api切面，负责进入Api前参数校验和业务异常处理
 */
@Slf4j
@Aspect
@Service
public class ApiAspect {

    /**
     * 针对Service接口添加环绕切面处理
     */
    @Around("execution(public * com.boilerplate.server.controller.*Controller.*(..))")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        long execTime = System.currentTimeMillis() - beginTime;
        CommonUtils.writeSystemLog(joinPoint, execTime, result);

        return result;
    }
}
