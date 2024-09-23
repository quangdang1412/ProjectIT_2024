package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.response.PageResponse;
import com.webbanhang.webbanhang.Model.ProductModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    List<ProductModel> getAllProduct();
    ProductModel findOneProduct(String id);
    boolean addProduct(ProductModel a);
    boolean updateProduct(ProductModel a);
    boolean deleteProduct(ProductModel a);
    List<ProductModel> findCategory(String id);
    Page<ProductModel> findCategoryForPage(String id,Integer a);
    Page<ProductModel> getProductForPage(Integer a);
    PageResponse<?> getAllProductWithSortBy(int pageNo, int pageSize, String sortBy);
}
