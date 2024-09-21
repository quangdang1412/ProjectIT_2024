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
public class CategoryRequestDTO implements Serializable {
        @NotBlank(message = "CategoryID must be not blank")
        @NotNull
        private String categoryID;
        @NotBlank(message = "CategoryName must be not blank")
        @NotNull
        private String categoryName;
}
