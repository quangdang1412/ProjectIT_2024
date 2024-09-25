package com.webbanhang.webbanhang.Controller.admin;


import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Service.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/admin"})
public class ProductController {

    private final IProductService productService;

    private final IBrandService brandService;

    private final ICategoryService categoryService;

    private final IDiscountService discountService;

    private final ISuppilerService supplierService;


    @GetMapping(value ={"/Product","/"})
    public String checkActionGet(Model model,@RequestParam Map<String,String> allParams, RedirectAttributes redirectAttributes)
    {
        String action = allParams.get("action");
        String id =allParams.get("id");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "new":
                return addProductForm(model);
            case "edit":
                return updateProductForm(model,id);
            default:
                return listProduct(model);

        }
    }
    @PostMapping("/Product")
    public String checkActionPost(Model model,RedirectAttributes redirectAttributes,@RequestParam Map<String,String> allParams,@RequestParam("ImageCode") MultipartFile file,@ModelAttribute("Product") ProductModel user)
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
    public String listProduct(Model model) {

        model.addAttribute("listProduct", productService.getAllProduct());

        return "/admin/Product/admin-home";
    }
    public String updateProductForm(Model model,String id) {
        ProductModel oldProduct = productService.getProductByID(id);
        model.addAttribute("Product",oldProduct);
        model.addAttribute("listProduct", productService.getAllProduct());
        model.addAttribute("listBrand", brandService.getAllBrand());
        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("listDiscount", discountService.getAllDiscount());
        model.addAttribute("listSupplier", supplierService.getAllSupplier());
        model.addAttribute("checkPro","update");

        String discountID;
        String imageCode;
        if (oldProduct.getDiscount() != null) {
            discountID = oldProduct.getDiscount().getDiscountID();
        }
        else
            discountID="";
        if (oldProduct.getImage() != null) {
            imageCode = oldProduct.getImage().getImageCode();
        }
        else
            imageCode="";
        model.addAttribute("ProductID", oldProduct.getProductID());
        model.addAttribute("ProductName", oldProduct.getProductName());
        model.addAttribute("BrandName", oldProduct.getBrand().getBrandName());
        model.addAttribute("CategoryName", oldProduct.getCategory().getCategoryName());
        model.addAttribute("DiscountID", discountID);
        model.addAttribute("ImageProduct", imageCode);
        model.addAttribute("Description", oldProduct.getDescription());
        model.addAttribute("UnitPrice", oldProduct.getUnitPrice());
        model.addAttribute("Quantity", oldProduct.getQuantity());
        model.addAttribute("UnitCost",oldProduct.getUnitCost());
        model.addAttribute("SupplierName",oldProduct.getSupplier().getSupplierName());
        return "/admin/Product/AddProduct";
    }
    public String addProductForm(Model model) {

        model.addAttribute("listProduct", productService.getAllProduct());
        model.addAttribute("listBrand", brandService.getAllBrand());
        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("listDiscount", discountService.getAllDiscount());
        model.addAttribute("listSupplier", supplierService.getAllSupplier());
        int x= productService.getAllProduct().size()+1;
        String s="P"+x;
        model.addAttribute("ProductID",s);
        model.addAttribute("checkPro",null);
        model.addAttribute("Product",new ProductModel());
        return "/admin/Product/AddProduct";
    }
}

