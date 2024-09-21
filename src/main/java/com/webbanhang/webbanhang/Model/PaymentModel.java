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
@Table(name = "payment")
public class PaymentModel {
    @Id
    @OneToOne()
    @JoinColumn(name = "OrderID")
    @JsonBackReference
    private OrderModel OrderPayment;
    @Column
    private String Method;
    @Column
    private String Status;

}
