package com.boilerplate.server.entity.area;

import lombok.Data;

import java.util.List;

@Data
public class AreaList<T> {
    //是否存在下一页
    private Boolean hasNext;
    //泛型数据列表
    private List<T> list;

    public AreaList(Boolean hasNext, List<T> list){
        this.hasNext = hasNext;
        this.list = list;
    }
}
