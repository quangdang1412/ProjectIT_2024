package com.webbanhang.webbanhang.Controller.api;

import com.webbanhang.webbanhang.DTO.request.Other.DashboardDateDTO;
import com.webbanhang.webbanhang.DTO.response.DashboardResponse;
import com.webbanhang.webbanhang.DTO.response.ResponseData;
import com.webbanhang.webbanhang.Service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/dashboard")
@Validated
@Slf4j
@RequiredArgsConstructor
public class DashboardAPI {
    private final IOrderService orderService;
    @PostMapping("/chartArea")
    public ResponseData<?> dashboard(@RequestBody DashboardDateDTO dashboardDateDTO){
        LocalDate startDate = dashboardDateDTO.getStartDate();
        LocalDate endDate = dashboardDateDTO.getEndDate();
        DashboardResponse dashboardResponse = orderService.dataChart(startDate,endDate);
        return new ResponseData<>(HttpStatus.CREATED.value(),"Success",dashboardResponse);
    }
}
