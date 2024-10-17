package com.webbanhang.webbanhang.Config.Controller.web;

import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Service.IProductService;
import com.webbanhang.webbanhang.Service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckOutController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ICartService cartService;

    public void loadProduct(Model model,HttpSession session) {
        UserModel a = (UserModel) session.getAttribute("UserLogin");
        List<CartModel> carts = new ArrayList<>();
        double totalAmount = 0.0;
        int quantity = 0;
        if(a.getUserCart()!= null)
        {
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
        LocalDate today = LocalDate.now();
        LocalDate fastDay = today.plusDays(4);
        LocalDate normalDay = today.plusDays(8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String id = "O"+ (orderService.getAllOrder().size()+1);
        model.addAttribute("OrderID",id);
        model.addAttribute("fastDate",fastDay.format(formatter));
        model.addAttribute("normalDate",normalDay.format(formatter));
        model.addAttribute("carts", carts);
        model.addAttribute("totalAmount",totalAmount);
        model.addAttribute("quantity",quantity);

    }
    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session,@RequestParam(required = false) String status,@RequestParam(required = false) String cancel) {
        if((UserModel)session.getAttribute("UserLogin") != null) {

            loadProduct(model,session);
            model.addAttribute("status", status);
            model.addAttribute("cancel", cancel);
            return "/web/checkout";
        }
        return "redirect:/login";
    }
}
