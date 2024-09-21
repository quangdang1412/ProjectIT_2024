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
@Table(name = "supplier")
public class SupplierModel {
    @Id
    @Column
    private String SupplierID;
    @Column
    private String SupplierName;
    @Column
    private String Address;
    @Column
    private String Phone;
    @Column
    private String Email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Supplier", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductModel> products;


}
