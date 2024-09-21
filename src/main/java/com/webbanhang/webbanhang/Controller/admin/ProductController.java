package com.webbanhang.webbanhang.Controller.admin;


import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Service.*;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.File;
import java.io.IOException;
import java.util.Map;


@Controller
@RequestMapping(value = {"/admin"})
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IDiscountService discountService;
    @Autowired
    private ISuppilerService suppilerService;

    @Autowired
    private IImageService imageService;

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
            case "insert":
                return addProduct(model,redirectAttributes,allParams,file);
            case "update":
                return updateProduct(model,redirectAttributes,allParams,file);
            default:
                return checkActionGet(model,allParams,redirectAttributes);

        }
    }
    public String listProduct(Model model) {

        model.addAttribute("listProduct", productService.getAllProduct());

        return "/admin/Product/admin-home";
    }
    public String updateProductForm(Model model,String id) {
        ProductModel oldProduct = productService.findOneProduct(id);
        model.addAttribute("Product",oldProduct);
        model.addAttribute("listProduct", productService.getAllProduct());
        model.addAttribute("listBrand", brandService.getAllBrand());
        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("listDiscount", discountService.getAllDiscount());
        model.addAttribute("listSupplier",suppilerService.getAllSupplier());
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
        model.addAttribute("listSupplier",suppilerService.getAllSupplier());
        int x= productService.getAllProduct().size()+1;
        String s="P"+x;
        model.addAttribute("ProductID",s);
        model.addAttribute("checkPro",null);
        model.addAttribute("Product",new ProductModel());
        return "/admin/Product/AddProduct";
    }
    public String addProduct(Model model, RedirectAttributes redirectAttributes,@RequestParam Map<String,String> allParams,@RequestParam("ImageCode") MultipartFile file) {
        ProductModel a = (ProductModel) model.getAttribute("Product");
        BrandModel brand = brandService.findBrandByID(allParams.get("BrandID"));
        CategoryModel category = categoryService.findCategoryByID(allParams.get("CategoryID"));
        DiscountModel discount = discountService.findDiscountByID(allParams.get("DiscountID"));
        SupplierModel supplier = suppilerService.findSupplierByID(allParams.get("SupplierID"));

        ImageModel imageProduct = null;
        try {
            String fileName = file.getOriginalFilename();
            if(imageService.findOneImage(fileName)==null)
            {
                String uploadPath = new File("src/main/resources/static/ImageProduct/").getAbsolutePath();

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String filePath = uploadPath + File.separator + fileName;
                file.transferTo(new File(filePath));
                imageService.addImage(fileName);
            }
            imageProduct = imageService.findOneImage(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "File upload failed: " + e.getMessage());
            return "redirect:/admin/Product?action=new";
        }
        a.setBrand(brand);
        a.setCategory(category);
        a.setDiscount(discount);
        a.setSupplier(supplier);
        a.setImage(imageProduct);
        a.setDeleteProduct(1);
        boolean success = productService.addProduct(a);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Thêm thất bại!");
            redirectAttributes.addFlashAttribute("ProductID", a.getProductID());
            redirectAttributes.addFlashAttribute("ProductName", a.getProductName());
            redirectAttributes.addFlashAttribute("BrandName", a.getBrand().getBrandName());
            redirectAttributes.addFlashAttribute("CategoryName", a.getCategory().getCategoryName());
            redirectAttributes.addFlashAttribute("DiscountID", a.getDiscount().getDiscountID());
            redirectAttributes.addFlashAttribute("ImageProduct", a.getImage().getImageCode());
            redirectAttributes.addFlashAttribute("Description", a.getDescription());
            redirectAttributes.addFlashAttribute("UnitPrice", a.getUnitPrice());
            redirectAttributes.addFlashAttribute("Quantity", a.getQuantity());
            redirectAttributes.addFlashAttribute("UnitCost", a.getUnitCost());
            redirectAttributes.addFlashAttribute("SupplierName",a.getSupplier().getSupplierName());
        }
        return "redirect:/admin/Product?action=new";
    }
    public String updateProduct(Model model, RedirectAttributes redirectAttributes,@RequestParam Map<String,String> allParams,@RequestParam("ImageCode") MultipartFile file) {
        ProductModel oldProduct = productService.findOneProduct(allParams.get("ProductID"));
        BrandModel brand = brandService.findBrandByID(allParams.get("BrandID"));
        CategoryModel category = categoryService.findCategoryByID(allParams.get("CategoryID"));
        DiscountModel discount = discountService.findDiscountByID(allParams.get("DiscountID"));
        SupplierModel supplier = suppilerService.findSupplierByID(allParams.get("SupplierID"));
        Double unitPrice = Double.parseDouble(allParams.get("UnitPrice"));
        Double unitCost = Double.parseDouble(allParams.get("UnitCost"));
        Integer quantity = Integer.parseInt(allParams.get("Quantity"));
        String name = allParams.get("ProductName");
        String description = allParams.get("Description");
        ImageModel imageProduct = null;
        try {
            String fileName = file.getOriginalFilename();
            if(imageService.findOneImage(fileName)==null && !fileName.isEmpty())
            {
                String uploadPath = new File("src/main/resources/static/ImageProduct/").getAbsolutePath();

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String filePath = uploadPath + File.separator + fileName;
                file.transferTo(new File(filePath));
                imageService.addImage(fileName);
                imageProduct = imageService.findOneImage(fileName);
            }
            else if(imageService.findOneImage(fileName)!=null && !fileName.isEmpty())
                    imageProduct=imageService.findOneImage(fileName);
            else
                    imageProduct = oldProduct.getImage();
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "File upload failed: " + e.getMessage());
        }
        oldProduct.setProductName(name);
        oldProduct.setQuantity(quantity);
        oldProduct.setUnitPrice(unitPrice);
        oldProduct.setUnitCost(unitCost);
        oldProduct.setBrand(brand);
        oldProduct.setDescription(description);
        oldProduct.setCategory(category);
        oldProduct.setDiscount(discount);
        oldProduct.setSupplier(supplier);
        oldProduct.setImage(imageProduct);
        oldProduct.setDeleteProduct(1);
        boolean success = productService.updateProduct(oldProduct);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật thất bại!");
            redirectAttributes.addFlashAttribute("ProductID", oldProduct.getProductID());
            redirectAttributes.addFlashAttribute("ProductName", oldProduct.getProductName());
            redirectAttributes.addFlashAttribute("BrandName", oldProduct.getBrand().getBrandName());
            redirectAttributes.addFlashAttribute("CategoryName", oldProduct.getCategory().getCategoryName());
            redirectAttributes.addFlashAttribute("DiscountID", oldProduct.getDiscount() != null ? oldProduct.getDiscount().getDiscountID() : null);
            redirectAttributes.addFlashAttribute("ImageProduct", oldProduct.getImage().getImageCode() != null ? oldProduct.getImage().getImageCode() : null);
            redirectAttributes.addFlashAttribute("Description", oldProduct.getDescription());
            redirectAttributes.addFlashAttribute("UnitPrice", oldProduct.getUnitPrice());
            redirectAttributes.addFlashAttribute("Quantity", oldProduct.getQuantity());
            redirectAttributes.addFlashAttribute("UnitCost", oldProduct.getUnitCost());
            redirectAttributes.addFlashAttribute("SupplierName",oldProduct.getSupplier().getSupplierName());
        }
        return "redirect:/admin/Product?action=edit&id="+oldProduct.getProductID();
    }
    @PutMapping(value ={"/deleteProduct/{productId}"})
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String id)
    {
        ProductModel oldProduct = productService.findOneProduct(id);
        String successMessage = null;
        String errorMessage = null;
        boolean success = productService.deleteProduct(oldProduct);
        if (success) {
            successMessage= "Đã xóa sản phẩm";
        } else {
            errorMessage ="Không thể xóa sản phẩm";
        }
        String responseMessage = successMessage != null ? successMessage : errorMessage;
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}

