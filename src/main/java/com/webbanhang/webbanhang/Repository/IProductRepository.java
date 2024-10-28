package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductRepository extends JpaRepository<ProductModel,String> {

    @Query("from ProductModel p where p.quantity<=6")
    List<ProductModel> getProductOutOfStock();
}
