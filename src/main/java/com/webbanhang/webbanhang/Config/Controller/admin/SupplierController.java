package com.webbanhang.webbanhang.Config.Controller.admin;

import com.webbanhang.webbanhang.Model.SupplierModel;
import com.webbanhang.webbanhang.Service.ISuppilerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping(value = {"/admin"})
public class SupplierController {
    @Autowired
    private ISuppilerService suppilerService;
    @GetMapping("/Supplier")
    public String checkActionGet(Model model, @RequestParam Map<String,String> allParams, HttpSession session)
    {
        if(!session.getAttribute("UserLoginRole").equals("ADMIN")) {
            return "redirect:/404";
        }
        String action = allParams.get("action");
        String id =allParams.get("id");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "new":
                return addSupplierForm(model);
            case "edit":
                return updateSupplierForm(model,id);
            default:
                return listSupplier(model);

        }

    }
    public String listSupplier(Model model) {

        model.addAttribute("listSupplier", suppilerService.getAllSupplier());
        return "/admin/Supplier/admin-supplier";
    }
    public String addSupplierForm(Model model)
    {
        model.addAttribute("checkSupplier",null);
        int x= suppilerService.getAllSupplier().size()+1;
        String s="S"+x;
        model.addAttribute("SupplierID",s);
        model.addAttribute("Supplier",new SupplierModel());
        return "/admin/Supplier/AddSupplier";
    }

    public String updateSupplierForm(Model model,String id)
    {
        SupplierModel a = suppilerService.findSupplierByID(id);
        model.addAttribute("checkSupplier","update");
        model.addAttribute("SupplierID",a.getSupplierID());
        model.addAttribute("SupplierName",a.getSupplierName());
        model.addAttribute("Phone",a.getPhone());
        model.addAttribute("Email",a.getEmail());
        model.addAttribute("Address",a.getAddress());
        return "/admin/Supplier/AddSupplier";
    }
}
