package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.ProductModel;

import java.util.List;

public interface ICartService {
    List<CartModel> getAllCart();
    List<ProductModel> getProductInCart(String id);
    CartModel findCart(String Uid,String Pid);
    boolean addCart(CartModel a);
    boolean updateCart(CartModel a);
    boolean deleteCart(CartModel a);
    CartModel findCartItemByUserAndProduct(String userId, ProductModel product);


}
