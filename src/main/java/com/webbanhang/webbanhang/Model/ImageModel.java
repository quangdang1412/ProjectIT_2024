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
@Entity
@Table(name = "image_tb")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String imageID;
    @Column
    private String imageCode;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "image", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ProductModel> products;

}
