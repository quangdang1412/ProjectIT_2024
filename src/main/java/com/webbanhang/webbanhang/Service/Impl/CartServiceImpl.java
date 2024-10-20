package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DAO.ICartDAO;
import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ICartDAO cartDAO;
    @Override
    public List<CartModel> getAllCart() {
        return cartDAO.getAllCart();
    }

    @Override
    public List<ProductModel> getProductInCart(String id) {
        return cartDAO.getProductInCart(id);
    }

    @Override
    public CartModel findCart(String Uid, String Pid) {
        return cartDAO.findCart(Uid,Pid);
    }

    @Override
    public boolean addCart(CartModel a) {
        return cartDAO.addCart(a);
    }

    @Override
    public boolean updateCart(CartModel a) {
        return cartDAO.updateCart(a);
    }

    @Override
    public boolean deleteCart(CartModel a) {
        return cartDAO.deleteCart(a);
    }
    @Override
    public CartModel findCartItemByUserAndProduct(String userId, ProductModel product) {
        return cartDAO.findCartItemByUserAndProduct(userId, product);
    }
}
