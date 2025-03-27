package com.webbanhang.webbanhang.DTO.request;

import com.webbanhang.webbanhang.DTO.request.Order.OrderRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestRabbitDTO implements Serializable {
    private String userId;
    private OrderRequestDTO orderRequestDTO;
}
