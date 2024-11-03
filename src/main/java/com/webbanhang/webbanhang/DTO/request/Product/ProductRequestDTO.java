package com.webbanhang.webbanhang.DTO.request.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO implements Serializable {

    @NotBlank(message = "productID must be not blank")
    @NotNull
    private String productID;

    @NotBlank(message = "productName must be not blank")
    @NotNull
    private String productName;

    @NotBlank(message = "brand must be not blank")
    @NotNull
    private String brand;

    @NotBlank(message = "category must be not blank")
    @NotNull
    private String category;

    private String discount;


    @NotBlank(message = "description must be not blank")
    @NotNull
    private String description;


    private Integer unitPrice;


    private Integer quantity;


    private Integer unitCost;
    @NotBlank(message = "supplierID must be not blank")
    @NotNull
    private String supplierID;

}
