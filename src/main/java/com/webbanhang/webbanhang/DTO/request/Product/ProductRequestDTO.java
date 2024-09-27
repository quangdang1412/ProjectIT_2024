package com.webbanhang.webbanhang.DTO.request.Product;

import com.webbanhang.webbanhang.Util.Validation.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

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


    private Double unitPrice;


    private Integer quantity;


    private Double unitCost;
    @NotBlank(message = "supplierID must be not blank")
    @NotNull
    private String supplierID;

}
