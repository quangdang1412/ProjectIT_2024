package com.webbanhang.webbanhang.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.webbanhang.webbanhang.Model.BrandModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.webbanhang.webbanhang.Model.CategoryModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Service.ICategoryService;
import com.webbanhang.webbanhang.Service.IOrderDetailService;
import com.webbanhang.webbanhang.Service.IProductService;

@Component
public class LoadData {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderDetailService orderDetailService;


    public void loadCategory(Model model, HttpSession session) {
        List<CategoryModel> categories = categoryService.getAllCategory();
        for (CategoryModel category : categories) {
            List<ProductModel> products = productService.findCategory(category.getCategoryID());
            category.setProducts(products);
            Set<BrandModel> brandSet = new HashSet<>();
            for (ProductModel productModel : products)
                brandSet.add(productModel.getBrand());
            category.setBrandSet(brandSet);
        }
        session.setAttribute("categories", categories);
        model.addAttribute("categories", categories);

    }

    public void loadProduct(Model model) {

        List<ProductModel> products = productService.getAllProduct();
        model.addAttribute("products", products);
    }

    public void loadOrderDetail(Model model) {

        List<Object[]> bestSellingProducts = orderDetailService.getBestSellingProducts();
        List<String> productIds = new ArrayList<>();

        for (int i = 0; i < Math.min(6, bestSellingProducts.size()); i++) {
            Object[] productInfo = bestSellingProducts.get(i);
            if (productInfo != null && productInfo.length > 0) {
                String productId = String.valueOf(productInfo[0]); // Chuyển đổi sang String nếu cần
                productIds.add(productId);
            }
        }
        List<ProductModel> products = new ArrayList<>();

        for (String productId : productIds) {
            ProductModel product = productService.getProductByID(productId);
            if (product != null) {
                products.add(product);
            }
        }

        model.addAttribute("bestSellingProducts", products);

    }

    public void ProductDiscount(Model mode) {
        List<ProductModel> products = productService.getAllProduct();
        List<ProductModel> productDiscount = new ArrayList<>();
        for (ProductModel product : products) {
            if (product.getDiscount() != null) {
                productDiscount.add(product);
            }
        }
        List<ProductModel> top5DiscountedProducts = productDiscount.stream().limit(6).collect(Collectors.toList());
        mode.addAttribute("productDiscount", top5DiscountedProducts);
    }

}
