package com.boilerplate.server.utils;

/**
 * 常用小工具类
 */
public class CommonUtils {
    public static String getLimitSql(Integer page, Integer pageSize) {
        return getLimitSql(page, pageSize, false);
    }

    public static String getLimitSql(Integer page, Integer pageSize, Boolean hasNext) {
        if (hasNext) {
            pageSize = pageSize + 1;
        }
        Integer offset = (page - 1) * pageSize;
        Integer limit = pageSize;
        return String.format("limit %s,%s", offset, limit);
    }
}
