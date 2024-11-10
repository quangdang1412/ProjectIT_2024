package com.webbanhang.webbanhang.Controller.api;

import com.webbanhang.webbanhang.DTO.request.Product.ProductRequestDTO;
import com.webbanhang.webbanhang.DTO.response.ProductDTO;
import com.webbanhang.webbanhang.DTO.response.ResponseData;
import com.webbanhang.webbanhang.DTO.response.ResponseError;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.IProductService;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductAPI {
    private final IProductService productService;
    private final IUserService userService;
    private final ICartService cartService;
    private final CheckLogin login;
    @PostMapping("/api/addtocart/{productId}/{quantity}")
    public ResponseEntity<?> addToCart(@PathVariable("productId") String productId,@PathVariable("quantity") int quantity,HttpSession session) {
       try{
           //checkLogin.checkLogin(session,model,userService);
           UserModel a = (UserModel) session.getAttribute("UserLogin");
           UserModel user = userService.findUserByID(a.getUserID());
           ProductModel product = productService.getProductByID(productId);
           CartModel cartItem = cartService.findCartItemByUserAndProduct(user.getUserID(), product);

           String successMessage = null;
           String errorMessage = null;
           if (cartItem != null) {
               cartItem.setQuantity(cartItem.getQuantity() + quantity);
               cartService.updateCart(cartItem);
               successMessage= "Đã cập nhật sản phẩm vào giỏ hàng";
               login.refreshUser(session);
           }
           else {
               CartModel newCartItem = new CartModel(user, product, quantity);
               boolean success = cartService.addCart(newCartItem);
               if (success) {
                   successMessage= "Đã cập nhật sản phẩm vào giỏ hàng";
                   login.refreshUser(session);
               } else {
                   errorMessage ="Không thêm được sản phẩm vào giỏ hàng";
               }
           }
           String responseMessage = successMessage != null ? successMessage : errorMessage;
           login.refreshUser(session);
           return ResponseEntity.ok(responseMessage);
       }catch (Exception e){
           log.error(e.getMessage());
           return ResponseEntity.ok("Không thêm được sản phẩm vào giỏ hàng");
       }
    }
    @DeleteMapping("/api/deleteproduct/{productId}")
    public ResponseEntity<?> deleteProInCart(@PathVariable("productId") String id,HttpSession session)
    {
        UserModel a = (UserModel) session.getAttribute("UserLogin");
        ProductModel b = productService.getProductByID(id);
        CartModel c = cartService.findCart(a.getUserID(), b.getProductID());
        boolean success = cartService.deleteCart(c);
        String successMessage = null;
        String errorMessage = null;
        if (success) {
            successMessage= "Đã xóa sản phẩm khỏi giỏ hàng";
            login.refreshUser(session);
        } else {
            errorMessage ="Không thể xóa sản phẩm khỏi giỏ hàng";
        }
        String responseMessage = successMessage != null ? successMessage : errorMessage;
        login.refreshUser(session);
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
    @PostMapping(value ="/api/product/add")
    public ResponseData<?> addProduct(@RequestParam Map<String,String> allParams, @RequestParam(value = "ImageCode")  MultipartFile fileImage)
    {
        try {
            log.info("Request add Product: {}", allParams.get("ProductName"));
            checkEmpty(allParams);
            if(fileImage.isEmpty())
                throw new CustomException("Image cannot be blank");
            ProductRequestDTO productRequestDTO = changeToDTO(allParams);
            return new ResponseData<>(HttpStatus.OK.value(),"Success",productService.saveProduct(productRequestDTO,fileImage));
        }
        catch (Exception e ){
            log.info("errorMessage={}",e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        }

    }
    @PutMapping(value ="/api/product/update")
    public ResponseData<?> updateProduct(@RequestParam Map<String,String> allParams, @RequestParam(value = "ImageCode", required = false) MultipartFile fileImage)
    {
        try {
            log.info("Request update Product: {}",allParams.get("ProductName"));
            checkEmpty(allParams);
            ProductRequestDTO productRequestDTO = changeToDTO(allParams);
            return new ResponseData<>(HttpStatus.OK.value(),"Success",productService.saveProduct(productRequestDTO,fileImage));
        }
        catch (Exception e ){
            log.info("errorMessage={}",e.getMessage(),e.getCause());
            String error = e.getMessage();
            if(error.contains("bounds for length"))
                error = "Failed";
            return new ResponseError(HttpStatus.BAD_REQUEST.value(),error);
        }
    }
    @DeleteMapping(value ="/api/product/delete/{proID}")
    public ResponseData<?> deleteProduct(@PathVariable String proID)
    {
        try {
            log.info("Request add order: 1");
            return new ResponseData<>(HttpStatus.OK.value(),"Success",productService.deleteProduct(proID));

        }
        catch (Exception e ){
            log.info("errorMessage={}",e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        }

    }
    void checkEmpty(Map<String,String> allParams){

        if (allParams.get("ProductName").isEmpty()) {
            throw new CustomException("Product name cannot be blank");
        }
        if (allParams.get("BrandID").isEmpty()) {
            throw new CustomException("Brand cannot be blank");
        }
        if (allParams.get("SupplierID").isEmpty()) {
            throw new CustomException("Supplier cannot be blank");
        }
        if (allParams.get("CategoryID").isEmpty()) {
            throw new CustomException("Category cannot be blank");
        }
        if (allParams.get("Description").isEmpty()) {
            throw new CustomException("Description cannot be blank");
        }
        if (allParams.get("Quantity").isEmpty() || Double.parseDouble(allParams.get("Quantity")) <= 0) {
            throw new CustomException("Quantity must be greater than zero");
        }
        if (allParams.get("UnitCost").isEmpty() || Double.parseDouble(allParams.get("UnitCost")) <= 0) {
            throw new CustomException("Unit cost must be greater than zero");
        }
        if (allParams.get("UnitPrice").isEmpty() || Double.parseDouble(allParams.get("UnitPrice")) <= 0 || Double.parseDouble(allParams.get("UnitPrice")) < Double.parseDouble(allParams.get("UnitCost"))) {
            throw new CustomException("Unit price must be greater than unit cost and greater than zero");
        }
    }
    ProductRequestDTO changeToDTO(Map<String,String> allParams){
        return ProductRequestDTO.builder()
                .productID(allParams.get("ProductID"))
                .productName(allParams.get("ProductName"))
                .brand(allParams.get("BrandID"))
                .unitPrice(Integer.valueOf(allParams.get("UnitPrice")))
                .unitCost(Integer.valueOf(allParams.get("UnitCost")))
                .supplierID(allParams.get("SupplierID"))
                .description(allParams.get("Description"))
                .category(allParams.get("CategoryID"))
                .discount(allParams.get("DiscountID").isEmpty() ? "empty" : allParams.get("DiscountID"))
                .quantity(Integer.valueOf(allParams.get("Quantity")))
                .build();
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "search", required = false) String searchQuery,
            @RequestParam(name = "categoryId", required = false) String categoryId) {

        Page<ProductDTO> productPage = productService.searchProducts(pageNo, pageSize, searchQuery,categoryId);

        Map<String, Object> response = new HashMap<>();
        response.put("content", productPage.getContent());
        response.put("currentPage", productPage.getNumber() + 1);
        response.put("totalPages", productPage.getTotalPages());
        response.put("totalItems", productPage.getTotalElements());

        return ResponseEntity.ok(response);
    }
}
