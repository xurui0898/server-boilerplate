package com.boilerplate.server.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试专用service类
 */
@Slf4j
public class TestService {
    /**
     * 日期时间格式化与时间戳转换，不能使用SimpleDateFormat有大坑
     */
    public static void datetimeHandle() {
        System.out.println("===========时间日期转换==============");
        //common-lang3工具类实现
        String format = "yyyy-MM-dd HH:mm:ss";
        long currentTime = System.currentTimeMillis();

        //时间戳转日期时间
        String datetime = DateFormatUtils.format(new Date(), format);
        String datetime2 = DateFormatUtils.format(currentTime, format);
        System.out.println(String.format("最新时间戳=%s，转换日期时间=%s",currentTime,datetime2));

        //时间字符串转unix时间戳，注意：getTime获取的是毫秒时间戳
        String strDate = "2022-12-01 19:23:44";
        try {
            Date parseDate = DateUtils.parseDateStrictly(strDate, format);
            long unixTimestamp = parseDate.getTime()/1000;
            System.out.println(String.format("[1]时间字符串=%s，转换时间戳=%s",strDate,unixTimestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //hutool日期工具类转换
        String strDate2 = "2022/12/1 19:23:44";
        Date date2 = DateUtil.parse(strDate2);
        long unixTimestamp2 = date2.getTime()/1000;
        System.out.println(String.format("[2]时间字符串=%s，转换时间戳=%s",strDate2,unixTimestamp2));
    }

    /**
     * 字符串处理
     * 使用StringUtils工具类
     */
    public static void stringHandle() {
        System.out.println("===========字符串处理==============");
        //多个字符串拼接
        String s1 = null;
        String s2 = "拼接字符串abc";
        System.out.println(StringUtils.join(s1,s2));
        //字符串分割为数组
        String s3 = "椰树椰汁,坚持在海南岛,用新鲜椰肉,鲜榨";
        String[] sList = StringUtils.split(s3, ",");
        System.out.println(Arrays.toString(sList));
        //list拆分拼接为字符串
        List<String> list = Lists.newArrayList("椰树椰汁","坚持在海南岛","用新鲜椰肉鲜榨");
        String strJoin = StringUtils.join(list, "");
        System.out.println(strJoin);
    }

    /**
     * 如何正确删除list的元素
     * 禁止在foreach时删除元素，会报错
     */
    public static void removeList() {
        System.out.println("===========list处理==============");
        List<String> list = Lists.newArrayList("北京市","海淀区","上地","清河","西二旗","上地","西三旗","西北旺");
        String delStr = "海淀区";
        System.out.println("list删除前："+list);
        //错误方法：禁止在foreach时删除元素，会报错
        /*for (String s : list) {
            if (delStr.equals(s)) {
                list.remove(s);
            }
        }*/
        //正确删除元素 方法1
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (delStr.equals(s)) {
                it.remove();
            }
        }
        //正确删除元素 方法2
        list = list.stream().filter(f-> !f.contains(delStr)).collect(Collectors.toList());
        //正确删除元素 方法3（推荐使用）
        list.removeIf(s-> s.equals(delStr) || s.contains("北京") || s.contains("旗"));

        //list去重
        list = list.stream().distinct().collect(Collectors.toList());
        System.out.println("list删除后："+list);
    }

    /**
     * 如何正确进行加减乘除数学运算
     */
    public static void mathCalculate() {
        System.out.println("===========数学运算==============");
        double a = 0.06;
        double b = 0.01;
        System.out.println("加法结果："+ArithService.add(a,b));
        System.out.println("减法结果："+ArithService.sub(a,b));
        System.out.println("乘法结果："+ArithService.mul(a,b));
        System.out.println("除法结果："+ArithService.div(a,b,2));
        //hutool工具类做数学运算-推荐
        double sub = NumberUtil.sub(a, b);
        System.out.println("hutool工具减法结果："+sub);
        //科学计数法显示处理
        double r = ArithService.mul(a,b);
        String str = new BigDecimal(String.valueOf(r)).toString();
        System.out.println("科学计数法显示结果："+str);
    }
    
}
