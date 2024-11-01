package com.webbanhang.webbanhang.DTO.request.Other;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class CouponRequestDTO {
    @NotBlank(message = "CouponID must be not blank")
    @NotNull
    private String couponID;
    @NotNull(message = " Percentage cannot be null")
    private Integer percentage;
}
