package com.webbanhang.webbanhang.DTO.request;

import com.webbanhang.webbanhang.DTO.request.Order.OrderEmailDTO;
import com.webbanhang.webbanhang.Model.OrderModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest implements Serializable {
    private OrderEmailDTO order;
    private int checkChangeStatus;
}
