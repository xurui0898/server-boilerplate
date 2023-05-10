package com.boilerplate.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 默认controller
 */
@RestController
@Slf4j
public class IndexController {
    public static void main(String[] args) {
        //时间日期
        timeHandle();
        //使用工具类进行字符串拼接
        String s1 = null;
        String s2 = "1678";
        System.out.println(StringUtils.join(s1,s2));
    }

    /**
     * 日期时间转换
     */
    public static void timeHandle() {
        //日期转换为时间戳
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());
        long formatTime = 0;
        try {
            formatTime = dateFormat.parse(currentDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("原日期时间={}，转换后时间戳={}",currentDate,formatTime);

        //时间戳转换为日期
        long currentTime = System.currentTimeMillis();
        String formatDate = dateFormat.format(new Date(currentTime));
        log.info("原时间戳={}，转换后日期时间={}",currentTime,formatDate);
    }
}
