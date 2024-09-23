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
@Table(name = "payment_tb")
public class PaymentModel {
    @Id
    @OneToOne()
    @JoinColumn(name = "orderID")
    @JsonBackReference
    private OrderModel orderPayment;
    @Column
    private String method;
    @Column
    private String status;

}
