package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="product")
public class ProductModel {
    @Id
    @Column
    private String ProductID;
    @Column
    private String ProductName;
    @ManyToOne()
    @JoinColumn(name = "BrandID")
    private BrandModel Brand;
    @ManyToOne()
    @JoinColumn(name = "CategoryID")
    private CategoryModel Category;
    @ManyToOne()
    @JoinColumn(name = "DiscountID")
    private DiscountModel Discount;
    @ManyToOne()
    @JoinColumn(name = "ImageID")
    private ImageModel Image;
    @Column
    private String Description;
    @Column
    private Double UnitPrice;
    @Column
    private Integer Quantity;
    @Column
    private Integer DeleteProduct;
    @Column
    private Double UnitCost;


    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "SupplierID")
    private SupplierModel Supplier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ProductCart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartModel> productCart;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ProductOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetailModel> productOrderDetail;
}
