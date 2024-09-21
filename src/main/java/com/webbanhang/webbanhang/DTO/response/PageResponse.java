package com.webbanhang.webbanhang.DTO.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
@Getter
@Builder
public class PageResponse<T> implements Serializable {
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private T items;
}
