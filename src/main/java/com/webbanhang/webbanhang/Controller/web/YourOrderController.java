package com.webbanhang.webbanhang.Controller.web;

import com.webbanhang.webbanhang.Model.OrderDetailModel;
import com.webbanhang.webbanhang.Model.OrderModel;
import com.webbanhang.webbanhang.Model.UserCouponModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Service.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class YourOrderController {
    private final IOrderService orderService;
    private final IUserService userService;

    @GetMapping("/yourOrder")
    public String yourOrder(Model model, @ModelAttribute("productId") String productId, HttpSession session) {
        if (session.getAttribute("UserLogin") != null) {
            UserModel a = (UserModel) session.getAttribute("UserLogin");
            orderService.getOrderByStatus("Chờ thanh toán");
            List<OrderModel> listOrder = userService.findUserByID(a.getUserID()).getUserOrder();
            model.addAttribute("listOrder", listOrder);
            return "/web/your-order";
        }
        return "redirect:/login";
    }

    @GetMapping("/yourOrder/updateorder")
    public String checkActionGet(Model model, @RequestParam Map<String, String> allParams, HttpSession session, String status, @RequestParam(required = false) String cancel) {
        //checkLogin.checkLogin(session,model,userService);
        UserModel a = (UserModel) session.getAttribute("UserLogin");

        String action = allParams.get("action");
        String id = allParams.get("orderID");
        OrderModel orderModel = orderService.getOrderByID(id);
        if (a == null || !orderModel.getUserOrder().getUserID().equals(a.getUserID()))
            return "redirect:/login";
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "edit":
                model.addAttribute("status", status);
                model.addAttribute("cancel", cancel);
                return updateOrderForm(model, id);
            default:
                return "redirect:/yourOrder";

        }
    }

    @PostMapping("/yourOrder/updateorder")
    public String checkActionPost(Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> allParams, @ModelAttribute("Order") OrderModel order, HttpSession session) {
        //checkLogin.checkLogin(session,model,userService);
        String action = allParams.get("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            default:
                return checkActionGet(model, allParams, session, "", "");

        }
    }

    public String updateOrderForm(Model model, String id) {
        OrderModel a = orderService.getOrderByID(id);
        List<OrderDetailModel> listOrderDetail = a.getOrderDetails();
        model.addAttribute("listOrderDetail", listOrderDetail);
        model.addAttribute("checkOrder", "update");
        model.addAttribute("OrderID", a.getOrderID());
        model.addAttribute("TransportFee", a.getTransportFee());
        model.addAttribute("TotalPrice", a.getTotalPrice());
        model.addAttribute("OrderDate", a.getOrderDate());
        model.addAttribute("Phone", a.getPhone());
        model.addAttribute("Address", a.getAddress());
        model.addAttribute("Name", a.getName());
        model.addAttribute("paymentStatus", a.getPaymentModel().getStatus());
        model.addAttribute("paymentMethod", a.getPaymentModel().getMethod());
        model.addAttribute("DeliveryTime", a.getDeliveryTime());
        model.addAttribute("Status", a.getStatus());
        model.addAttribute("Order", a);
        return "/web/your-orderdetail";
    }
}
