package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.ProductModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<ProductModel, String> {


    @Query("SELECT p FROM ProductModel p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%'))")
    Page<ProductModel> searchProducts(@Param("productName") String productName, Pageable pageable);


    @Query("from ProductModel p where p.quantity<=6")
    List<ProductModel> getProductOutOfStock();

    @Query("from ProductModel p where p.brand.brandID = :id")
    List<ProductModel> findByBrand(String id);

    List<ProductModel> findProductModelsByCategoryCategoryID(String id);

    @Transactional
    @Modifying
    @Query("UPDATE ProductModel p SET p.discount = NULL WHERE p.productID = :id")
    void setDiscountForProduct(String id);

    Page<ProductModel> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);

    Page<ProductModel> findByCategory_categoryID(String categoryId, Pageable pageable);

    @Query("SELECT p FROM ProductModel p WHERE p.active = true AND (:categoryID IS NULL OR lower(p.category.categoryID) LIKE lower(:categoryID)) AND (:brandID IS NULL OR lower(p.brand.brandID) LIKE lower(:brandID)) AND (:searchQuery IS NULL OR lower(p.productName) LIKE lower(:searchQuery))")
    Page<ProductModel> getProductForPage(
            @Param("categoryID") String categoryID,
            @Param("brandID") String brandID,
            @Param("searchQuery") String searchQuery,
            Pageable pageable
    );

}
