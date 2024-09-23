package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SupplierModel")
@Table(name = "supplier_tb")
public class SupplierModel {
    @Id
    @Column
    private String supplierID;
    @Column
    private String supplierName;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ProductModel> products;


}
