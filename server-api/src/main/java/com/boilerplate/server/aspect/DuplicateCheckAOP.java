package com.boilerplate.server.aspect;

import cn.hutool.json.JSONUtil;
import com.boilerplate.server.annotation.DuplicateCheck;
import com.boilerplate.server.exception.DuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
        //获取注解的值
        String key = check.key();
        long expireTime = check.expireTime();
        //获取方法入参Map
        Map<String, Object> paramMap = getParamMap(joinPoint);
        log.info("DuplicateCheck注解执行, key={},expireTime={},参数={}",key,expireTime,JSONUtil.toJsonStr(paramMap));

        if (paramMap.get(key) != null) {
            String methodName = signature.getName();
            String lockName = String.format("lock:%s:%s", methodName, paramMap.get(key).toString());
            if (lockName != null) {
                log.info("lockName={}",lockName);
                throw new DuplicateException("请求处理中，请勿重复提交!");
            }
        }
    }

    /**
     * 获取方法入参Map
     * @param joinPoint
     * @return
     */
    private Map<String, Object> getParamMap(JoinPoint joinPoint) {
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();

        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            paramMap.put(paramNames[i], paramValues[i]);
        }
        return paramMap;
    }
}
