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
@Table(name = "user_tb")
public class UserModel {
    @Id
    @Column
    private String userID;

    @Column
    private String userName;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String gender;
    @Column
    private String email;
    @Column
    private String password;

    @ManyToOne()
    @JsonManagedReference
    @JoinColumn(name = "type")
    private RoleModel role;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userCart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartModel> userCart;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> userOrder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sellerOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> sellerOrder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shipperOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> shipperOrder;
}
