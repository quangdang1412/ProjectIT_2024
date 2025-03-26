package com.webbanhang.webbanhang.DTO.request.Order;

import com.webbanhang.webbanhang.Model.OrderDetailModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEmailDTO implements Serializable {
    private String orderID;
    private String phone;
    private List<OrderDetailModel> orderDetails;
    private String address;
    private String status;
    private String userEmail;
}
