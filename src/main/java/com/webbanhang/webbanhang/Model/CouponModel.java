package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon_tb")
public class CouponModel {
    @Id
    private String couponID;
    @Column
    private Integer percentage;
    @Column
    private boolean active;
}
