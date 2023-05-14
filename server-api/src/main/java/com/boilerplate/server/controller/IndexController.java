package com.boilerplate.server.controller;

import com.boilerplate.server.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认controller
 */
@RestController
@Slf4j
public class IndexController {

    public static void main(String[] args) {
        //日期时间格式化与时间戳转换
        TestService.datetimeHandle();
        //StringUtils工具类 字符串处理
        TestService.stringHandle();
        //正确删除list中的元素
        TestService.removeList();
    }
}
