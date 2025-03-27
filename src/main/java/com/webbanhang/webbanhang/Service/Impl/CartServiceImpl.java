package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.PK.UserCartID;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Repository.ICartRepository;
import com.webbanhang.webbanhang.Service.ICartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements ICartService {

    private final ICartRepository cartRepository;

    @Override
    public List<CartModel> getAllCart() {
        return cartRepository.findAll();
    }

    @Override
    public List<CartModel> getAllCartOfUser(String id) {
        return cartRepository.getAllCartOfUser(id);
    }

    @Override
    public List<ProductModel> getProductInCart(String id) {
        return cartRepository.getProductInCart(id);
    }

    @Override
    public CartModel findCart(UserModel Uid, ProductModel Pid) {
        return cartRepository.findById(new UserCartID(Uid, Pid)).orElse(null);
    }

    @Override
    public boolean addCart(CartModel a) {
        try {
            cartRepository.save(a);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateCart(CartModel a) {
        try {
            cartRepository.save(a);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteCart(CartModel a) {
        try {
            cartRepository.delete(a);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void deleteByUserCartUserID(String a) {
        try {
            cartRepository.deleteByUserCartUserID(a);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
