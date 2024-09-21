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
@Table(name = "category")
public class CategoryModel {
    @Id
    @Column
    private String CategoryID;
    @Column
    private String CategoryName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Category", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductModel> products;

}
