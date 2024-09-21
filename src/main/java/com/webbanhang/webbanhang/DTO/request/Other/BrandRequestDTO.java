package com.webbanhang.webbanhang.DTO.request.Other;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class BrandRequestDTO implements Serializable {
    @NotBlank(message = "BrandID must be not blank")
    @NotNull
    private String brandID;
    @NotBlank(message = "BrandName must be not blank")
    @NotNull
    private String brandName;
}
