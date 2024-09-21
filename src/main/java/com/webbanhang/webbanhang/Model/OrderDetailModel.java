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
@Table(name = "orderdetail")
public class OrderDetailModel {
    @Id
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "OrderID")
    private OrderModel Order;
    @Id
    @ManyToOne()
    @JoinColumn(name = "ProductID")
    private ProductModel ProductOrder;
    @Column
    private Integer Quantity;
}
