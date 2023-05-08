package com.boilerplate.server.controller;

import com.boilerplate.server.Util.Utils;
import com.boilerplate.server.entity.ApiResult;
import com.boilerplate.server.entity.Response;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 通用接口测试controller
 */
@RestController
@Slf4j
public class TestController {
    /**
     * 发送post请求
     * @return
     */
    @RequestMapping("/test/post")
    @ResponseBody
    public ApiResult<String> post() {
        String url = "http://api.map.baidu.com/geocoding/v3/?address=1&output=json&ak=1&callback=showLocation";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("source", "10086");
        params.add("value", "1");
        String response = Utils.sendPostRequest(url,params);

        ApiResult<String> apiResult = Response.makeOKRsp(response);
        log.info("apiResult={}",new Gson().toJson(apiResult));
        return apiResult;
    }

    /**
     * 发送get请求
     * @return
     */
    @RequestMapping("/test/get")
    @ResponseBody
    public ApiResult<String> get() {
        String url = "http://api.map.baidu.com/geocoding/v3/?address=1&output=json&ak=1&callback=showLocation";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("source", "10086")
                .queryParam("value", "1");
        url = builder.build().encode().toUriString();
        String response = Utils.sendGetRequest(url);

        ApiResult<String> apiResult = Response.makeOKRsp(response);
        log.info("apiResult={}",new Gson().toJson(apiResult));
        return apiResult;
    }
}
