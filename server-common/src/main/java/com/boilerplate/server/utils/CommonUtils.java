package com.boilerplate.server.utils;

import com.boilerplate.server.entity.SystemLog;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 常用小工具类
 */
@Slf4j
public class CommonUtils {
    public static String getLimitSql(Integer page, Integer pageSize) {
        return getLimitSql(page, pageSize, false);
    }

    public static String getLimitSql(Integer page, Integer pageSize, Boolean hasNext) {
        //hasNext：查询是否存在下一页，为true时pageSize多查一条记录
        Integer offset = (page - 1) * pageSize;
        Integer limit = hasNext ? pageSize + 1 : pageSize;
        return String.format("limit %s,%s", offset, limit);
    }

    /**
     * 记录系统日志
     * @param joinPoint
     * @param execTime
     * @param result
     */
    public static void writeSystemLog(ProceedingJoinPoint joinPoint, long execTime, Object result) {
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
