package com.webbanhang.webbanhang.Controller.web;

import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Service.IProductService;
import com.webbanhang.webbanhang.Util.LoadData;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShopDetailController {
    private final IProductService productService;

    private final LoadData loadData;

    @GetMapping("/detail/{productId}")
    public String detail(Model model, @ModelAttribute("productId") String productId, HttpSession session) {
        loadData.loadProduct(model);
        loadData.loadOrderDetail(model);
        loadData.loadCategory(model, session);
        ProductModel product = productService.getProductByID(productId);
        List<ProductModel> productDiscount = new ArrayList<>();
        for (ProductModel x : productService.getAllProduct()) {
            if (x.getDiscount() != null)
                productDiscount.add(x);
        }
        model.addAttribute("product", product);
        model.addAttribute("productDiscount", productDiscount);
        return "/web/shop-detail";
    }
}
