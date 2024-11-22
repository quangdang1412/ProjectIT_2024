package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.UserModel;

import java.util.List;

public interface ICartService {
    List<CartModel> getAllCart();

    List<ProductModel> getProductInCart(String id);

    CartModel findCart(UserModel Uid, ProductModel Pid);

    boolean addCart(CartModel a);

    boolean updateCart(CartModel a);

    boolean deleteCart(CartModel a);

    void deleteByUserCartUserID(String a);
}
