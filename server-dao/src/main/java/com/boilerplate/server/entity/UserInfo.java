package com.boilerplate.server.entity;


import lombok.Data;

/**
 * 测试自定义模型
 */
@Data
public class UserInfo {
    private Long userId;
    private String userName;
    private String address;
    private String feature;
}
