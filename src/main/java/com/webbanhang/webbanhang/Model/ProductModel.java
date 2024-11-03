package com.webbanhang.webbanhang.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private Integer unitPrice;
    @Column
    private Integer quantity;
    @Column
    private boolean active;
    @Column
    private Integer unitCost;


    @ManyToOne()
    @JoinColumn(name = "supplierID")
    private SupplierModel supplier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productCart", cascade = CascadeType.ALL)
    private List<CartModel> productCart;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productOrder", cascade = CascadeType.ALL)
    private List<OrderDetailModel> productOrderDetail;
}
