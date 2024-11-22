package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.webbanhang.webbanhang.Model.PK.UserCartID;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(UserCartID.class)
@Table(name = "shopping_cart_tb")
public class CartModel {
    @Id
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "userID")
    private UserModel userCart;
    @Id
    @ManyToOne()
    @JoinColumn(name = "productID")
    private ProductModel productCart;
    @Column
    private Integer quantity;
}
