package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationModel {
    @JsonProperty("proviceId")
    private String ProviceId;

    @JsonProperty("provinceName")
    private String ProvinceName;

    @JsonProperty("districtId")
    private String DistrictId;

    @JsonProperty("districtName")
    private String DistrictName;

    @JsonProperty("communeId")
    private String CommuneId;

    @JsonProperty("communeName")
    private String CommuneName;

}
