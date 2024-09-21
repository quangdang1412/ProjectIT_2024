package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "brand")
public class BrandModel {
    @Id
    @Column
    private String BrandID;

    @Column
    private String BrandName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Brand", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductModel> products;
}