package com.boilerplate.server.utils;

/**
 * 常用小工具类
 */
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
}
