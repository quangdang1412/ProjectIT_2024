package com.webbanhang.webbanhang.Controller.api;

import com.webbanhang.webbanhang.DTO.response.ResponseData;
import com.webbanhang.webbanhang.DTO.response.ResponseError;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.ICategoryService;
import com.webbanhang.webbanhang.Service.IProductService;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import com.webbanhang.webbanhang.Util.LoadData;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductAPI {

    private final ICategoryService categoryService;
    private final IProductService productService;
    private final IUserService userService;
    private final ICartService cartService;
    private final LoadData loadData;
    private final CheckLogin checkLogin = new CheckLogin();
    @PostMapping("/api/addtocart/{productId}/{quantity}")
    public ResponseEntity<?> addToCart(@PathVariable("productId") String productId,@PathVariable("quantity") int quantity, Model model, HttpSession session) {
        checkLogin.checkLogin(session,model,userService);
        UserModel user = (UserModel) session.getAttribute("UserLogin");
        ProductModel product = productService.findOneProduct(productId);
        CartModel cartItem = cartService.findCartItemByUserAndProduct(user.getUserID(), product);

        String successMessage = null;
        String errorMessage = null;
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartService.updateCart(cartItem);
            successMessage= "Đã cập nhật sản phẩm vào giỏ hàng";
        }
        else {
            CartModel newCartItem = new CartModel(user, product, quantity);
            boolean success = cartService.addCart(newCartItem);
            if (success) {
                successMessage= "Đã cập nhật sản phẩm vào giỏ hàng";
            } else {
                errorMessage ="Không thêm được sản phẩm vào giỏ hàng";
            }
        }
        String responseMessage = successMessage != null ? successMessage : errorMessage;
        return ResponseEntity.ok(responseMessage);
    }
    @DeleteMapping("/api/deleteproduct/{productId}")
    public ResponseEntity<?> deleteProInCart(@PathVariable("productId") String id,HttpSession session)
    {
        UserModel a = (UserModel) session.getAttribute("UserLogin");
        ProductModel b = productService.findOneProduct(id);
        CartModel c = cartService.findCart(a.getUserID(), b.getProductID());
        boolean success = cartService.deleteCart(c);
        String successMessage = null;
        String errorMessage = null;
        if (success) {
            successMessage= "Đã xóa sản phẩm khỏi giỏ hàng";
        } else {
            errorMessage ="Không thể xóa sản phẩm khỏi giỏ hàng";
        }
        String responseMessage = successMessage != null ? successMessage : errorMessage;
        return ResponseEntity.ok(responseMessage);
    }
    @GetMapping("/api/product/getAllProduct")
    public ResponseData<?> getAllProduct(@RequestParam(defaultValue = "1", required = false) @Min(value = 1, message = "pageNo must be greater than 1")  int pageNo,
                                   @Valid @Min(value = 10,  message = "pageNo must be greater than 10") @RequestParam(defaultValue = "1", required = false) int pageSize,
                                   @RequestParam(required = false) String sortBy)
    {

        try {
            return new ResponseData<>(HttpStatus.OK.value(),"Get user successfully",productService.getAllProductWithSortBy(pageNo,pageSize,sortBy));
        }
        catch (ResourceNotFoundException e){
            log.info("errorMessage={}",e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        }

    }
}
