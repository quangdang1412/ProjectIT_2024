package com.webbanhang.webbanhang.Model.PK;

import com.webbanhang.webbanhang.Model.OrderModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailID implements Serializable {
    private OrderModel order;
    private ProductModel productOrder;
}
