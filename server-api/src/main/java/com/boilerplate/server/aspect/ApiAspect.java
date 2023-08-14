package com.boilerplate.server.aspect;

import com.boilerplate.server.entity.SystemLog;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Object aroundController(ProceedingJoinPoint joinPoint) throws IllegalAccessException, InstantiationException {
        try {
            long beginTime = System.currentTimeMillis();
            Object result = joinPoint.proceed(joinPoint.getArgs());
            long execTime = System.currentTimeMillis() - beginTime;
            writeSystemLog(joinPoint, execTime, result);

            return result;
        } catch (Throwable throwable) {
            log.info("[请求异常]" + throwable.toString());
            return null;
        }
    }

    /**
     * 记录请求日志
     * @param joinPoint
     * @param execTime
     * @param result
     */
    private void writeSystemLog(ProceedingJoinPoint joinPoint, long execTime, Object result) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SystemLog systemLog = new SystemLog();
        systemLog.setExecTime(execTime);
        systemLog.setCreateTime(dateFormat.format(new Date()));

        //请求的 类名、方法名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        systemLog.setClassName(className);
        systemLog.setMethodName(methodName);
        //请求的参数 结果
        systemLog.setResult(result);
        Object[] args = joinPoint.getArgs();
        try{
            List<String> list = new ArrayList<>();
            for (Object o : args) {
                list.add(new Gson().toJson(o));
            }
            systemLog.setParams(list.toString());
            log.info(new Gson().toJson(systemLog));
        }catch (Exception e){ }
    }
}
