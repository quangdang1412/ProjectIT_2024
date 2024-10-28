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
@Table(name ="product_tb")
public class ProductModel {
    @Id
    @Column
    private String productID;
    @Column
    private String productName;
    @ManyToOne()
    @JoinColumn(name = "brandID")
    private BrandModel brand;
    @ManyToOne()
    @JoinColumn(name = "categoryID")
    private CategoryModel category;
    @ManyToOne()
    @JoinColumn(name = "discountID")
    private DiscountModel discount;
    @ManyToOne()
    @JoinColumn(name = "imageID")
    private ImageModel image;
    @Column
    private String description;
    @Column
    private Double unitPrice;
    @Column
    private Integer quantity;
    @Column
    private boolean active;
    @Column
    private Double unitCost;


    @ManyToOne()
    @JoinColumn(name = "supplierID")
    private SupplierModel supplier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productCart", cascade = CascadeType.ALL)
    private List<CartModel> productCart;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productOrder", cascade = CascadeType.ALL)
    private List<OrderDetailModel> productOrderDetail;
}
