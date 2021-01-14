package com.boilerplate.server.Util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

public class Utils {
    /**
     * 向URL发送post请求
     * @param url
     * @param params
     * @return
     */
    public static String sendPostRequest(String url, MultiValueMap<String, String> params){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        //以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //设置中文编码
        restTemplate.getMessageConverters().add(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
    }
}
