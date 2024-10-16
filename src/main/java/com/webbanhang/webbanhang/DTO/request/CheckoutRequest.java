package com.webbanhang.webbanhang.DTO.request;

import java.util.List;

import lombok.Data;
import vn.payos.type.ItemData;

@Data
public class CheckoutRequest {
    private Integer finalTotal;
    private String orderId;
    private String returnUrl;
    private String cancelUrl;
}
