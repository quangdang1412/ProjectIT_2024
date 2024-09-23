package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OrderModel")
@Table(name ="shop_order_tb")
public class OrderModel {
    @Id
    @Column
    private String orderID;
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "userID")
    private UserModel userOrder;
    @ManyToOne()
    @JoinColumn(name = "sellerID")
    private UserModel sellerOrder;
    @ManyToOne()
    @JoinColumn(name = "shipperID")
    private UserModel shipperOrder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetailModel> orderDetails;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "orderPayment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private PaymentModel PaymentModel;

    @Column
    private Date orderDate;
    @Column
    private Double totalPrice;
    @Column
    private Double transportFee;
    @Column
    private Date deliveryTime;
    @Column
    private String status;
    @Column
    private String phone;
    @Column
    private String name;
    @Column
    private String address;
}
