package com.boilerplate.server.entity;

import lombok.Data;

import java.util.List;

@Data
public class ApiList<T> {
    //是否存在下一页
    private Boolean hasNext;
    //泛型数据列表
    private List<T> list;

    public ApiList(Boolean hasNext, List<T> list){
        this.hasNext = hasNext;
        this.list = list;
    }
}
