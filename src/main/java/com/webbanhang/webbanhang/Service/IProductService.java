package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.Product.ProductRequestDTO;
import com.webbanhang.webbanhang.DTO.response.PageResponse;
import com.webbanhang.webbanhang.DTO.response.ProductDTO;
import com.webbanhang.webbanhang.Model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    List<ProductModel> getAllProduct();

    ProductModel getProductByID(String id);

    String saveProduct(ProductRequestDTO productRequestDTO, MultipartFile file);

    String deleteProduct(String a);

    List<ProductModel> findCategory(String id);

    List<ProductModel> findBrand(String id);

    List<ProductModel> getProductOutOfStock();

    Page<ProductModel> getProductForPage(Integer a, String categoryID, String brandID, String sortBy, String searchQuery);

    PageResponse<?> getAllProductWithSortBy(int pageNo, int pageSize, String sortBy);

    Page<ProductDTO> searchProducts(int pageNo, int pageSize, String searchQuery, String categoryId);

}
