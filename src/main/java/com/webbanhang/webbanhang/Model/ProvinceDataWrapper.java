package com.webbanhang.webbanhang.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProvinceDataWrapper {

    @JsonProperty("Sheet1")
    private List<ProvinceModel> provinces;

    public List<ProvinceModel> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvinceModel> provinces) {
        this.provinces = provinces;
    }
}