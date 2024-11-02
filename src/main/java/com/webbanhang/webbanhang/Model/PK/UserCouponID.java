package com.webbanhang.webbanhang.Model.PK;

import com.webbanhang.webbanhang.Model.CouponModel;
import com.webbanhang.webbanhang.Model.UserModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCouponID implements Serializable {
    private UserModel userCoupon;
    private CouponModel couponUser;
}
