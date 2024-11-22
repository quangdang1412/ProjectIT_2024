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
@Table(name = "user_coupon_tb")
public class UserCouponModel {
    @Id
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "userID")
    private UserModel userCoupon;
    @Id
    @ManyToOne()
    @JoinColumn(name = "couponID")
    private CouponModel couponUser;
    @Column
    private Integer quantity;
}
