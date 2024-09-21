package com.webbanhang.webbanhang.DTO.request.Supplier;

import com.webbanhang.webbanhang.Util.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierRequestDTO implements Serializable {
    @NotBlank(message = "SupplierID must be not blank")
    @NotNull
    private String supplierID;

    @NotBlank(message = "SupplierName must be not blank")
    @NotNull
    private String supplierName;

    @Email(message = "email invalid format")
    @NotBlank(message = "email must be not blank")
    @NotNull
    private String email;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotBlank(message = "Address must be not blank")
    private String address;
}
