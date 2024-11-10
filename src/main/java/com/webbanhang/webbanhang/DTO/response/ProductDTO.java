package com.webbanhang.webbanhang.DTO.response;

import com.google.auto.value.AutoValue.Builder;
import com.webbanhang.webbanhang.Model.BrandModel;
import com.webbanhang.webbanhang.Model.CategoryModel;
import com.webbanhang.webbanhang.Model.DiscountModel;
import com.webbanhang.webbanhang.Model.ImageModel;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ProductDTO {
    private String productID;
    private String productName;
    private BrandModel brand;
    private CategoryModel category;
    private DiscountModel discount;
    private ImageModel image;
    private String description;
    private Integer unitPrice;
    private Integer quantity;
    private boolean active;
    private Integer unitCost;
}
