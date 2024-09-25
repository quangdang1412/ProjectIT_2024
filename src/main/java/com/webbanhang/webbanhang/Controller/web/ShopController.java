package com.webbanhang.webbanhang.Controller.web;

import java.util.List;

import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import com.webbanhang.webbanhang.Util.LoadData;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.webbanhang.webbanhang.Model.CategoryModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Service.ICategoryService;
import com.webbanhang.webbanhang.Service.IProductService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ShopController {
    private final ICategoryService  categoryService;
    private final IProductService productService;

    private final IUserService userService;

    private final ICartService cartService;

    private final LoadData loadData;

    private final CheckLogin checkLogin;

    public void loadCategory(Model model) {

        List<CategoryModel> categories = categoryService.getAllCategory();
        for (CategoryModel category : categories) {
            List<ProductModel> products = productService.findCategory(category.getCategoryID());
            category.setProducts(products);
        }
        model.addAttribute("categories", categories);

    }

    public void productPage(Model model,int pageNo)
    {
        Page<ProductModel> products = productService.getProductForPage(pageNo);
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("products", products);
    }

    

    @GetMapping("/shop")
    public String shop(Model model,@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,HttpSession session){
        loadCategory(model);
        productPage(model,pageNo);
        loadData.ProductDiscount(model);
        checkLogin.checkLogin(session,model,userService);
        return "/web/shop";
    }
    @GetMapping("/shop/category/{categoryId}")
    public String shopByCategory(@PathVariable("categoryId") String categoryId, Model model,@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,HttpSession session) {
        loadCategory(model);
        Page<ProductModel> products = productService.findCategoryForPage(categoryId,pageNo);
        model.addAttribute("products", products);
        checkLogin.checkLogin(session,model,userService);
        return "/web/shop";
    }
    @GetMapping("/shop/addtocart/{productId}")
    public String addToCart(@PathVariable("productId") String productId, Model model, HttpSession session,RedirectAttributes redirectAttributes) {
        checkLogin.checkLogin(session,model,userService);
        UserModel user = (UserModel) session.getAttribute("UserLogin");
        ProductModel product = productService.getProductByID(productId);
        CartModel cartItem = cartService.findCartItemByUserAndProduct(user.getUserID(), product);

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
        return "redirect:/shop";
    }

    
}