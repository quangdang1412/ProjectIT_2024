package com.webbanhang.webbanhang.Controller.web;

import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.LoadData;

import jakarta.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private IUserService userService;

    @Autowired
    private  LoadData loadData;
    @RequestMapping(value = {"/", "/index","/static"})
    public String home(Model model,HttpSession session) {
        loadData.loadProduct(model);
        loadData.loadOrderDetail(model);
        loadData.loadCategory(model,session);
        return "/web/index";
    }
    @GetMapping("/contact")
    public String contact(Model model,HttpSession session) {
        return "/web/contact";
    }

    @GetMapping("/testimonial")
    public String testimonial() {
        return "/web/testimonial";
    }

    @GetMapping("/shop-detail")
    public String shopDetail(Model model,HttpSession session){
        return "/web/shop-detail";
    }

    @GetMapping("/login")
    public String login() {
        return "/web/login";
    }


    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("SignUp","right-panel-active");
        return "/web/login";
    }

    @GetMapping("/inforuser")
    public String inforuser(Model model, HttpSession session){
        UserModel a = (UserModel) session.getAttribute("UserLogin");
        UserModel b = userService.findUserByID(a.getUserID());
        model.addAttribute("User",b);
        return "/web/inforUser";
    }
    @GetMapping("/list")
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserModel> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/test")
    public String test(){
        return "/web/test";
    }
}