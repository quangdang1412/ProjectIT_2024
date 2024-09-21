package com.webbanhang.webbanhang.Controller.web;

import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.IProductService;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import com.webbanhang.webbanhang.Util.LoadData;
import jakarta.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopDetailController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private  LoadData loadData;


    private CheckLogin checkLogin = new CheckLogin();



    @GetMapping("/detail/{productId}")
    public String detail(Model model, @ModelAttribute("productId") String productId,HttpSession session) {
        checkLogin.checkLogin(session,model,userService);
        loadData.loadProduct(model);
        loadData.loadOrderDetail(model);
        loadData.loadCategory(model);
        ProductModel product = productService.findOneProduct(productId);
        List<ProductModel> productDiscount = new ArrayList<>();
        for(ProductModel x : productService.getAllProduct())
        {
            if(x.getDiscount()!= null)
                productDiscount.add(x);
        }
        model.addAttribute("product", product);
        model.addAttribute("productDiscount",productDiscount);
        return "/web/shop-detail";
    }
}
