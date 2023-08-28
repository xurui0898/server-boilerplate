package com.boilerplate.server.sharding;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class ShardingUtils {
    //sharding-jdbc 分库分表数据源名称
    public static final String SHARDING_DATA_SOURCE_NAME = "sharding-data-source";

    /**
     * 生成唯一订单号
     * 当前年月日6位+当天运行秒数5位+随机数3位+用户ID后5位=19位
     */
    public static Long genOrderId(Long userId){
        Date date = DateUtil.date();
        //当前年月日-6位
        String ymd = StringUtils.right(DateUtil.format(date, DatePattern.PURE_DATE_PATTERN),6);
        //当天运行秒数-5位
        Date beginOfDay = DateUtil.beginOfDay(date);
        long between = DateUtil.between(beginOfDay, date, DateUnit.SECOND);
        String betSecond = StringUtils.leftPad(String.valueOf(between), 5, "0");
        //生成随机数-3位
        String ranNum = StringUtils.leftPad(String.valueOf(RandomUtil.randomInt(1, 999)), 3, "0");
        //截取用户ID后5位
        String subUserId = StringUtils.leftPad(StringUtils.right(String.valueOf(userId), 5), 5, "0");

        return Long.valueOf(ymd+betSecond+ranNum+subUserId);
    }
}
