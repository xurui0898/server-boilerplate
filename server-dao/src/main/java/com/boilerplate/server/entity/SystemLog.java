package com.boilerplate.server.entity;

import lombok.Data;

@Data
public class SystemLog {
    private String className;

    private String methodName;

    private String params;

    private Object result;

    private Long execTime;

    private String createTime;
}
