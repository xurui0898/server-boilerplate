package com.boilerplate.server.aspect;

import com.boilerplate.server.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Api切面，负责进入Api前参数校验和业务异常处理
 */
@Slf4j
@Aspect
@Service
public class ApiAspect {
    @Autowired
    private CommonService commonService;

    /**
     * 针对Service接口添加环绕切面处理
     */
    @Around("execution(public * com.boilerplate.server.controller.*Controller.*(..))")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws IllegalAccessException, InstantiationException {
        try {
            long beginTime = System.currentTimeMillis();
            Object result = joinPoint.proceed(joinPoint.getArgs());
            long execTime = System.currentTimeMillis() - beginTime;
            commonService.writeSystemLog(joinPoint, execTime, result);

            return result;
        } catch (Throwable throwable) {
            log.info("[请求异常]" + throwable.toString());
            return null;
        }
    }
}
