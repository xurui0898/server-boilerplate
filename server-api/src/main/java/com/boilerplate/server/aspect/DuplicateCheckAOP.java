package com.boilerplate.server.aspect;

import cn.hutool.json.JSONUtil;
import com.boilerplate.server.annotation.DuplicateCheck;
import com.boilerplate.server.exception.DuplicateException;
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
public class DuplicateCheckAOP {
    @Pointcut("@annotation(com.boilerplate.server.annotation.DuplicateCheck)")
    public void DuplicateCheck(){}

    @Before("DuplicateCheck()")
    public void before(JoinPoint joinPoint) throws DuplicateException {
        //根据MethodSignature获取注解对象
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        DuplicateCheck check = signature.getMethod().getAnnotation(DuplicateCheck.class);
        Object[] args = joinPoint.getArgs();
        //获取注解的值
        String reqId = check.reqId();
        log.info("DuplicateCheck 切点执行, reqId={}, 参数={}",reqId,JSONUtil.toJsonStr(args));
        if (args[1] != null && Integer.parseInt(args[1].toString()) > 1) {
            throw new DuplicateException("请求处理中，请勿重复提交");
        }
    }
}
