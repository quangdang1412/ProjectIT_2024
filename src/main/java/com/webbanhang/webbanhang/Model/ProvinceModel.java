package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProvinceModel {
    @JsonProperty("provinceName")
    private String provinceName;

    @JsonProperty("provinceId")
    private String provinceId;

    @JsonProperty("districtName")
    private String districtName;

    @JsonProperty("districtId")
    private String districtId;

    @JsonProperty("communeName")
    private String communeName;

    @JsonProperty("communeId")
    private String communeId;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getCommuneName() {
        return communeName;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public String getCommuneId() {
        return communeId;
    }

    public void setCommuneId(String communeId) {
        this.communeId = communeId;
    }
    
}
