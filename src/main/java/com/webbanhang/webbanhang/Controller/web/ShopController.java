package com.webbanhang.webbanhang.Controller.web;

import com.webbanhang.webbanhang.DTO.response.ProductDTO;
import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Service.IBrandService;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.ICategoryService;
import com.webbanhang.webbanhang.Service.IProductService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import com.webbanhang.webbanhang.Util.LoadData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShopController {
    private final ICategoryService categoryService;
    private final IBrandService brandService;
    private final IProductService productService;
    private final ICartService cartService;
    private final LoadData loadData;
    private final CheckLogin login;

    public void loadCategory(Model model) {

        List<CategoryModel> categories = categoryService.getAllCategory();
        for (CategoryModel category : categories) {
            List<ProductModel> products = productService.findCategory(category.getCategoryID());
            category.setProducts(products);
        }
        List<BrandModel> brands = brandService.getAllBrand();
        for (BrandModel brand : brands) {
            List<ProductModel> products = productService.findBrand(brand.getBrandID());
            brand.setProducts(products);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);

    }

    public void productPage(Model model, int pageNo, String categoryID, String brandID, String sortBy, String searchQuery) {
        Page<?> products = productService.getProductForPage(pageNo, categoryID, brandID, sortBy, searchQuery);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
    }

    @GetMapping("/shop")

    public String shop(Model model,
                       @RequestParam(name = "search", required = false) String searchQuery,
                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(name = "category", required = false) String categoryID,
                       @RequestParam(name = "brand", required = false) String brandID,
                       @RequestParam(name = "sortBy", required = false) String sortBy,
                       HttpServletRequest servletRequest, HttpSession session) {

        loadCategory(model);
        loadData.ProductDiscount(model);
        login.refreshUser(session);
        productPage(model, pageNo, categoryID, brandID, sortBy, searchQuery);
        String queryString = servletRequest.getQueryString();
        if (!StringUtils.isEmpty(queryString) && queryString.contains("&pageNo=")) {
            int startIndex = queryString.indexOf("&pageNo=");
            if (startIndex != -1) {
                queryString = queryString.substring(0, startIndex);
            }
        }
        model.addAttribute("currentURL", servletRequest.getRequestURL());
        model.addAttribute("queryString", queryString);
        //checkLogin.checkLogin(session,model,userService);
        return "web/shop";
    }


    @GetMapping("/shop/addtocart/{productId}")
    public String addToCart(@PathVariable("productId") String productId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        //checkLogin.checkLogin(session,model,userService);
        UserModel user = (UserModel) session.getAttribute("UserLogin");
        ProductModel product = productService.getProductByID(productId);
        CartModel cartItem = cartService.findCart(user, product);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartService.updateCart(cartItem);
            redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật sản phẩm vào giỏ hàng");
        } else {
            CartModel newCartItem = new CartModel(user, product, 1);
            boolean success = cartService.addCart(newCartItem);
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "Đã thêm sản phẩm vào giỏ hàng");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không thêm được sản phẩm vào giỏ hàng");
            }
        }
        return "redirect:shop";
    }


}