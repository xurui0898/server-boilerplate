package com.boilerplate.server.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认controller
 */
@RestController
@Slf4j
public class IndexController {
    public static void main(String[] args) {
        //日期时间格式化与时间戳转换
        timeHandle();
        //正确删除list中的元素
        removeList();
        //使用工具类进行字符串拼接
        String s1 = null;
        String s2 = "椰树椰汁·坚持在海南岛用新鲜椰肉鲜榨";
        System.out.println(StringUtils.join(s1,s2));
    }

    /**
     * 演示：如何正确删除list的元素
     * 禁止在foreach时删除元素，会报错
     */
    public static void removeList() {
        List<String> list = Lists.newArrayList("北京市","海淀区","上地","清河");
        String delStr = "海淀区";
        System.out.println("原数据："+list);
        //错误方法：禁止在foreach时删除元素，会报错
        /*for (String s : list) {
            if (delStr.equals(s)) {
                list.remove(s);
            }
        }*/
        //正确方法1
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (delStr.equals(s)) {
                it.remove();
            }
        }
        //正确方法2
        list = list.stream().filter(f-> !f.contains(delStr)).collect(Collectors.toList());
        //正确方法3
        list.removeIf(s-> s.equals(delStr) || s.equals("北京市"));
        System.out.println("新数据："+list);
    }

    /**
     * 日期时间格式化与时间戳转换
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
