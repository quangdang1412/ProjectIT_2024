package com.webbanhang.webbanhang.Config.Controller.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webbanhang.webbanhang.Model.ProvinceModel;
import com.webbanhang.webbanhang.Service.LocationService;


@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public List<ProvinceModel> getLocationData() throws IOException {
        return locationService.readLocationData();
    }

}
