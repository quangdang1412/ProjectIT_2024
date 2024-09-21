package com.webbanhang.webbanhang.Service;

import java.io.IOException;
import java.util.List;
import java.io.File;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbanhang.webbanhang.Model.ProvinceDataWrapper;
import com.webbanhang.webbanhang.Model.ProvinceModel;

@Service
public class LocationService {

    @Value("${json.file.path}")
    private String jsonFilePath;

    public List<ProvinceModel> readLocationData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProvinceDataWrapper dataWrapper = objectMapper.readValue(new File(jsonFilePath), ProvinceDataWrapper.class);
        return dataWrapper.getProvinces();
    }
}