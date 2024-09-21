package com.webbanhang.webbanhang.Controller.admin;

import com.webbanhang.webbanhang.Model.BrandModel;
import com.webbanhang.webbanhang.Model.CategoryModel;
import com.webbanhang.webbanhang.Model.DiscountModel;
import com.webbanhang.webbanhang.Service.IBrandService;
import com.webbanhang.webbanhang.Service.ICategoryService;
import com.webbanhang.webbanhang.Service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping(value = {"/admin"})
public class OtherController {
    @Autowired
    private IDiscountService discountService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IBrandService brandService;
    @GetMapping("/Other")
    public String checkActionGet(@RequestParam Map<String,String> allParams, Model model)
    {
        String type = allParams.get("type");
        String action = allParams.get("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "new":
                return handleNewAction(type, model);
            case "edit":
                return handleEditAction(type,allParams, model);

            default:
                return handleListAction(type, model);
        }
    }
    private String handleNewAction(String type, Model model) {
        switch (type) {
            case "3":
                return addBrandForm(model);
            case "1":
                return addDiscountForm(model);
            default:
                return addCategoryForm(model);
        }
    }
    private String handleEditAction(String type,@RequestParam Map<String,String> allParams, Model model) {
        switch (type) {
            case "3":
                return updateBrandForm(allParams,model);
            case "1":
                return updateDiscountForm(allParams,model);
            default:
                return updateCategoryForm(allParams,model);
        }
    }
    private String handleListAction(String type, Model model) {
        switch (type) {
            case "3":
                return listBrand(model);
            case "1":
                return listDiscount(model);
            default:
                return listCategory(model);
        }
    }
    @PostMapping("/Other")
    public String checkActionPost(@RequestParam Map<String,String> allParams, Model model, RedirectAttributes redirectAttributes
                                 ,@ModelAttribute("Brand") BrandModel brand,@ModelAttribute("Category") CategoryModel category,@ModelAttribute("Discount") DiscountModel discount)
    {
        String type = allParams.get("type");
        String action = allParams.get("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            default:
                return handleListAction(type, model);
        }
    }

    public String listCategory(Model model) {

        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("title","Danh mục");
        model.addAttribute("example","example");
        model.addAttribute("type","2");
        return "/admin/Other/admin-other";
    }
    public String listBrand(Model model) {

        model.addAttribute("listBrand", brandService.getAllBrand());
        model.addAttribute("title","Thương hiệu");
        model.addAttribute("example2","example");
        model.addAttribute("type","3");
        return "/admin/Other/admin-other";
    }
    public String listDiscount(Model model) {

        model.addAttribute("listDiscount", discountService.getAllDiscount());
        model.addAttribute("title","Khuyến mãi");
        model.addAttribute("example1","example");
        model.addAttribute("type","1");
        return "/admin/Other/admin-other";
    }
    public String addBrandForm(Model model)
    {
        model.addAttribute("checkBrand",null);
        int x= brandService.getAllBrand().size()+1;
        String s="B"+x;
        model.addAttribute("BrandID",s);
        model.addAttribute("title","Thông tin thương hiệu");
        model.addAttribute("example","Brand");
        model.addAttribute("type","3");
        model.addAttribute("Brand",new BrandModel());
        return "/admin/Other/AddOther";
    }
    public String addCategoryForm(Model model)
    {
        model.addAttribute("checkCategory",null);
        int x= categoryService.getAllCategory().size()+1;
        String s="C"+x;
        model.addAttribute("CategoryID",s);
        model.addAttribute("title","Thông tin danh mục");
        model.addAttribute("example","Category");
        model.addAttribute("type","2");
        model.addAttribute("Category",new CategoryModel());
        return "/admin/Other/AddOther";
    }
    public String addDiscountForm(Model model)
    {
        model.addAttribute("checkDiscount",null);
        int x= discountService.getAllDiscount().size()+1;
        String s="D"+x;
        model.addAttribute("DiscountID",s);
        model.addAttribute("title","Thông tin khuyến mãi");
        model.addAttribute("example","Discount");
        model.addAttribute("type","1");
        model.addAttribute("Discount",new DiscountModel());
        return "/admin/Other/AddOther";
    }
    public String updateBrandForm(@RequestParam Map<String,String> allParams, Model model)
    {
        model.addAttribute("checkBrand","update");
        BrandModel a= brandService.findBrandByID(allParams.get("id"));
        model.addAttribute("BrandID",a.getBrandID());
        model.addAttribute("BrandName",a.getBrandName());
        model.addAttribute("title","Thông tin thương hiệu");
        model.addAttribute("example","Brand");
        model.addAttribute("type","3");
        return "/admin/Other/AddOther";
    }
    public String updateCategoryForm(@RequestParam Map<String,String> allParams, Model model)
    {
        model.addAttribute("checkCategory","update");
        CategoryModel a= categoryService.findCategoryByID(allParams.get("id"));
        model.addAttribute("CategoryID",a.getCategoryID());
        model.addAttribute("CategoryName",a.getCategoryName());
        model.addAttribute("title","Thông tin danh mục");
        model.addAttribute("example","Category");
        model.addAttribute("type","2");
        return "/admin/Other/AddOther";
    }
    public String updateDiscountForm(@RequestParam Map<String,String> allParams, Model model)
    {
        model.addAttribute("checkDiscount","update");
        DiscountModel a= discountService.findDiscountByID(allParams.get("id"));
        model.addAttribute("DiscountID",a.getDiscountID());
        model.addAttribute("Percentage", a.getPercentage());
        model.addAttribute("StartDate",a.getStartDate());
        model.addAttribute("EndDate",a.getEndDate());
        model.addAttribute("title","Thông tin khuyến mãi");
        model.addAttribute("example","Discount");
        model.addAttribute("type","1");
        return "/admin/Other/AddOther";
    }



}
