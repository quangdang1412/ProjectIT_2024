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
@Table(name ="shop_order")
public class OrderModel {
    @Id
    @Column
    private String OrderID;
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "UserID")
    private UserModel UserOrder;
    @ManyToOne()
    @JoinColumn(name = "SellerID")
    private UserModel SellerOrder;
    @ManyToOne()
    @JoinColumn(name = "ShipperID")
    private UserModel ShipperOrder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetailModel> orderDetails;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "OrderPayment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private PaymentModel PaymentModel;

    @Column
    private Date OrderDate;
    @Column
    private Double TotalPrice;
    @Column
    private Double TransportFee;
    @Column
    private Date DeliveryTime;
    @Column
    private String Status;
    @Column
    private String Phone;
    @Column
    private String Name;
    @Column
    private String Address;
}
