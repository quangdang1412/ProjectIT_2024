package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<ProductModel,String> {
}
