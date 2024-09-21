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
@Table(name = "image")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String ImageID;
    @Column
    private String ImageCode;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Image", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductModel> products;

}
