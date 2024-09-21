package com.webbanhang.webbanhang.DTO.request.Other;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class DiscountRequestDTO implements Serializable {
    @NotBlank(message = "DiscountID must be not blank")
    @NotNull
    private String discountID;
    @NotNull(message = " Percentage cannot be null")
    private Integer percentage;
    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;
    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;
}
