package com.webbanhang.webbanhang.DTO.request.Order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    @NotBlank(message = "orderID must be not blank")
    @NotNull
    private String orderID;
    @NotBlank(message = "name must be not blank")
    @NotNull
    private String name;
    @NotBlank(message = "phone must be not blank")
    @NotNull
    private String phone;
    @NotBlank(message = "address must be not blank")
    @NotNull
    private String address;
    @NotBlank(message = "shippingOption must be not blank")
    @NotNull
    private String shippingOption;
    @NotBlank(message = "paymentMethod must be not blank")
    @NotNull
    private String paymentMethod;

    private String couponID;

}
