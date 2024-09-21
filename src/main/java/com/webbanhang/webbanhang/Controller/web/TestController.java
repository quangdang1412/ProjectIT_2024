package com.webbanhang.webbanhang.Controller.web;

import com.webbanhang.webbanhang.Model.BrandModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.IBrandService;
import com.webbanhang.webbanhang.Service.IProductService;
import com.webbanhang.webbanhang.Service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IProductService productService;


    @GetMapping("/user")
    public List<UserModel> get()
    {
        return userService.getAllUser();
    }
    @GetMapping("/oneproduct")
    public ProductModel oneProduct(HttpServletRequest request,Model model)
    {
        String id=(String)request.getParameter("id");
        return productService.findOneProduct(id);
    }
    @GetMapping("/brand")
    public BrandModel getBrand(HttpServletRequest request,Model model ){
        String s=(String)request.getParameter("BrandID");
        return brandService.findBrandByID(s);
    }
    @GetMapping("/brand1")
    public List<BrandModel> getBrand1(HttpServletRequest request, Model model){

        return brandService.getAllBrand();
    }
    @GetMapping(value = {"/home2"})
    public List<ProductModel> home1(Model model) {
        model.addAttribute("listProduct", productService.getAllProduct());
        return productService.getAllProduct();
    }
    @GetMapping(value = {"/user1"})
    public List<UserModel> user(Model model) {
        model.addAttribute("listProduct", userService.getAllUser());
        return userService.getAllUser();
    }

}
