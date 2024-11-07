package com.webbanhang.webbanhang.Controller.admin;

import com.webbanhang.webbanhang.Model.SupplierModel;
import com.webbanhang.webbanhang.Service.ISuppilerService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = {"/admin"})
@RequiredArgsConstructor
public class SupplierController {

    private final ISuppilerService suppilerService;
    private final CheckLogin checkLogin;
    @GetMapping("/Supplier")
    public String checkActionGet(Model model, @RequestParam Map<String,String> allParams, HttpSession session)
    {
        if(checkLogin.checkRoleAdmin(session)) {
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
        String s="S"+ UUID.randomUUID().toString().substring(0, 8);;
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
