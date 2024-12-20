package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.PK.UserCartID;
import com.webbanhang.webbanhang.Model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICartRepository extends JpaRepository<CartModel, UserCartID> {
    @Query("select c.productCart from CartModel c where c.userCart.userID = :id")
    List<ProductModel> getProductInCart(String id);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartModel c where c.userCart.userID = :id")
    void deleteByUserCartUserID(String id);
}
