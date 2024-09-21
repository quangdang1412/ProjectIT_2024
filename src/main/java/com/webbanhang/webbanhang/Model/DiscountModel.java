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
@Table(name = "discount")
public class DiscountModel {
    @Id
    @Column
    private String DiscountID;
    @Column
    private Integer Percentage;
    @Column
    private Date StartDate;
    @Column
    private Date EndDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Discount", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductModel> products;

}
