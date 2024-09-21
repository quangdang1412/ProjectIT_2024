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
@Table(name = "shopping_cart")
public class CartModel {
    @Id
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "UserID")
    private UserModel UserCart;
    @Id
    @ManyToOne()
    @JoinColumn(name = "ProductID")
    private ProductModel ProductCart;
    @Column
    private Integer Quantity;
}
