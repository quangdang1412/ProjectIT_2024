package com.webbanhang.webbanhang.Controller.web;

import com.webbanhang.webbanhang.Model.ProvinceModel;
import com.webbanhang.webbanhang.Service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/locations")
    public List<ProvinceModel> getLocationData() throws IOException {
        return locationService.readLocationData();
    }

}
