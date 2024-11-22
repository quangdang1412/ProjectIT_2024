package com.webbanhang.webbanhang.Controller.admin;

import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Service.IProductService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DashboardController {
    private final IOrderService orderService;
    private final IProductService productService;
    private final CheckLogin checkLogin;

    @GetMapping("/Dashboard")
    public String checkActionGet(Model model, HttpSession session) {
        if (checkLogin.checkRoleAdmin(session)) {
            return "redirect:/404";
        }
        model.addAttribute("orderNeedToProcess", orderService.orderNeedToProcess() == null ? 0 : orderService.orderNeedToProcess());
        model.addAttribute("revenueTotal", orderService.revenueTotal() == null ? 0 : orderService.revenueTotal());
        model.addAttribute("revenuePerMonth", orderService.revenuePerMonth() == null ? 0 : orderService.revenuePerMonth());
        model.addAttribute("profitTotal", orderService.profitTotal() == null ? 0 : orderService.profitTotal());
        model.addAttribute("listProduct", productService.getProductOutOfStock());
        return "/admin/dashboard";
    }
}
