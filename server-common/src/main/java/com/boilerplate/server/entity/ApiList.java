package com.boilerplate.server.entity;

import lombok.Data;
import java.util.List;

@Data
public class ApiList<T> {
    //是否存在下一页
    private Boolean hasNext;
    //泛型数据列表
    private List<T> list;

    public static <T> ApiList<T> makeResult(Boolean hasNext, List<T> list) {
        ApiList<T> result = new ApiList<>();
        result.hasNext = hasNext;
        result.list = list;
        return result;
    }
}
