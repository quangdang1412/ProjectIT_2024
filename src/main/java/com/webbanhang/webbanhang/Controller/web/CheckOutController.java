package com.webbanhang.webbanhang.Controller.web;

import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Service.IProductService;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String checkout(Model model, HttpSession session) {
        //checkLogin.checkLogin(session,model,userService);
        if((UserModel)session.getAttribute("UserLogin") != null) {
            loadProduct(model,session);
            return "/web/checkout";
        }
        return "redirect:/login";
    }
}
