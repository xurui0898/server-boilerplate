package com.boilerplate.server.controller;

import com.boilerplate.server.Util.Utils;
import com.boilerplate.server.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 通用测试controller
 */
@RestController
@Slf4j
public class TestController {
    public static void main(String[] args) {
        System.out.println("Test 测试");
    }

    /**
     * 日期时间转换
     * @return
     */
    @RequestMapping("/test/datetime")
    public Boolean datetime() {
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
        return true;
    }

    /**
     * 发送post请求
     * @return
     */
    @RequestMapping("/test/post")
    public String post() {
        String url = "http://api.map.baidu.com/geocoding/v3/?address=1&output=json&ak=1&callback=showLocation";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("source", "10086");
        params.add("value", "1");
        String response = Utils.sendPostRequest(url,params);
        log.info("response={}",response);
        return response;
    }

    /**
     * 发送get请求
     * @return
     */
    @RequestMapping("/test/get")
    public String get() {
        String url = "http://api.map.baidu.com/geocoding/v3/?address=1&output=json&ak=1&callback=showLocation";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("source", "10086")
                .queryParam("value", "1");
        url = builder.build().encode().toUriString();

        String response = Utils.sendGetRequest(url);
        log.info("response={}",response);
        return response;
    }
}
