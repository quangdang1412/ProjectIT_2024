package com.webbanhang.webbanhang.Controller.admin;

import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/admin"})
public class OrderController {
    private final IOrderService orderService;
    private final IUserService userService;
    // @GetMapping("/Order")
    // public String tesString(Model model)
    // {
    //     return "/web/test";
    // }
    @GetMapping("/Order")
    public String checkActionGet(Model model,@RequestParam Map<String,String> allParams, RedirectAttributes redirectAttributes)
    {
        String type = allParams.get("type");
        String action = allParams.get("action");
        String id =allParams.get("id");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "edit":
                return updateOrderForm(model,id);
            default:
                return listUser(model,type);

        }
    }
    @PostMapping("/Order")
    public String checkActionPost(Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String,String> allParams, @ModelAttribute("Order") OrderModel order)
    {
        String action = allParams.get("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            default:
                return checkActionGet(model,allParams,redirectAttributes);

        }
    }
    public String listUser(Model model,String type ) {
        if(type ==null)
        {
            model.addAttribute("listOrder", orderService.getOrderByStatus("Hoàn thành"));
        }
        else{
            switch (type) {
                case "1":
                    model.addAttribute("listOrder",orderService.getOrderByStatus("Chờ xác nhận"));
                    break;
                case "2":
                    model.addAttribute("listOrder",orderService.getOrderByStatus("Đã xác nhận"));
                    break;
                case "3":
                    model.addAttribute("listOrder",orderService.getOrderByStatus("Đang giao"));
                    break;
                default:
                    model.addAttribute("listOrder", orderService.getOrderByStatus("Hoàn thành"));
                    break;

            }
        }
        return "/admin/Order/admin-order";
    }
    public String updateOrderForm(Model model,String id)
    {
        OrderModel a = orderService.getOrderByID(id);
        List<OrderDetailModel> listOrderDetail = a.getOrderDetails();
        model.addAttribute("listOrderDetail",listOrderDetail);
        model.addAttribute("listSeller",userService.findByRole(3));
        model.addAttribute("listShipper",userService.findByRole(4));
        model.addAttribute("checkOrder","update");
        model.addAttribute("OrderID",a.getOrderID());
        model.addAttribute("UserID",a.getUserOrder());
        model.addAttribute("TransportFee",a.getTransportFee());
        model.addAttribute("TotalPrice",a.getTotalPrice());
        model.addAttribute("OrderDate",a.getOrderDate());
        model.addAttribute("Phone",a.getPhone());
        model.addAttribute("Address",a.getAddress());
        model.addAttribute("Name",a.getName());
        model.addAttribute("paymentStatus",a.getPaymentModel().getStatus());
        model.addAttribute("paymentMethod",a.getPaymentModel().getMethod());
        model.addAttribute("DeliveryTime",a.getDeliveryTime());
        model.addAttribute("Status",a.getStatus());
        model.addAttribute("sellerName",a.getSellerOrder() != null ? a.getSellerOrder().getUsername() : null);
        model.addAttribute("shipperName",a.getShipperOrder() != null ? a.getShipperOrder().getUsername() : null);
        model.addAttribute("Order",a);
        return "/admin/Order/AddOrder";
    }
}
