package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail_tb")
public class OrderDetailModel {
    @Id
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "orderID")
    private OrderModel order;
    @Id
    @ManyToOne()
    @JoinColumn(name = "productID")
    private ProductModel productOrder;
    @Column
    private Integer quantity;
}
