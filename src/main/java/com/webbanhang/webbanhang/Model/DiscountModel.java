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
@Entity
@Table(name = "discount_tb")
public class DiscountModel {
    @Id
    @Column
    private String discountID;
    @Column
    private Integer percentage;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private boolean active;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discount", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ProductModel> products;

}
