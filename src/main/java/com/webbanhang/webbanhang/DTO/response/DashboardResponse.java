package com.webbanhang.webbanhang.DTO.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class DashboardResponse implements Serializable {
    private List<String> time;
    private List<String> data;
    private List<String> top5;
    private List<String> dataTop5;

}
