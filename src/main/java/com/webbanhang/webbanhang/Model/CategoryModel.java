package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category_tb")
public class CategoryModel {
    @Id
    @Column
    private String categoryID;
    @Column
    private String categoryName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ProductModel> products;

    @Transient
    private Set<BrandModel> brandSet;
}
