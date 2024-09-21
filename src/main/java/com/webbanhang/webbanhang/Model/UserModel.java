package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Entity(name = "UserModel")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserModel {
    @Id
    @Column
    private String UserID;

    @Column
    private String UserName;
    @Column
    private String Address;
    @Column
    private String Phone;
    @Column
    private String Gender;
    @Column
    private String Email;
    @Column
    private String Password;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "Type")
    private RoleModel Role;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "UserCart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartModel> userCart;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "UserOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> userOrder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "SellerOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> sellerOrder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ShipperOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> shipperOrder;
}
