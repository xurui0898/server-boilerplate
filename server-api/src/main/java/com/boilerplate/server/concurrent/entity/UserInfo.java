package com.boilerplate.server.concurrent.entity;

import lombok.Data;

@Data
public class UserInfo {
    public Long userId;
    public String nickname;
    public String mobile;
    public String address;
}
