package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webbanhang.webbanhang.Model.PK.OrderDetailID;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(OrderDetailID.class)
@Table(name = "order_detail_tb")
public class OrderDetailModel {
    @Id
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "orderID")
    private OrderModel order;
    @Id
    @ManyToOne()
    @JoinColumn(name = "productID")
    private ProductModel productOrder;
    @Column
    private Integer quantity;
}
