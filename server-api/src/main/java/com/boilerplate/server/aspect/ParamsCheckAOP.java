package com.boilerplate.server.aspect;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ParamsCheckAOP {
    @Pointcut("@annotation(com.boilerplate.server.aspect.ParamsCheck)")
    public void ParamsCheck(){}

    @Before("ParamsCheck()")
    public void before(JoinPoint joinPoint){
        //根据MethodSignature获取注解对象
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        ParamsCheck check = signature.getMethod().getAnnotation(ParamsCheck.class);
        Object[] args = joinPoint.getArgs();
        //获取方法参数
        log.info("切点之前 before正常执行, 参数={}",JSONUtil.toJsonStr(args));
        //获取注解的值
        log.info("切点之前 before正常执行, paramsName={}",check.paramsName());
    }
}
