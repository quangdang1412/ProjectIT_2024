package com.webbanhang.webbanhang.Controller.web;


import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final IUserService userService;
    private final CheckLogin login;

    public void loadProduct(Model model, HttpSession session) {
        UserModel d = (UserModel) session.getAttribute("UserLogin");
        UserModel a = userService.findUserByID(d.getUserID());
        List<CartModel> carts = new ArrayList<>();
        double totalAmount = 0.0;
        int quantity = 0;
        if (a.getUserCart() != null) {
            carts = a.getUserCart();
            for (CartModel cart : carts) {
                if (cart.getProductCart().getDiscount() != null) {
                    totalAmount += cart.getProductCart().getUnitPrice() * (100 - cart.getProductCart().getDiscount().getPercentage()) / 100 * cart.getQuantity();
                } else {
                    totalAmount += cart.getProductCart().getUnitPrice() * cart.getQuantity();
                }
                quantity += cart.getQuantity();
            }
        }
        model.addAttribute("carts", carts);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("quantity", quantity);

    }

    @GetMapping("/cart")
    public String shop(Model model, HttpSession session) {
        if (session.getAttribute("UserLogin") != null) {
            login.refreshUser(session);
            loadProduct(model, session);
            return "web/cart";
        }
        return "redirect:login";
    }

}
