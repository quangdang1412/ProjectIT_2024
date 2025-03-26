package com.webbanhang.webbanhang.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webbanhang.webbanhang.DTO.request.Product.ProductRequestDTO;
import com.webbanhang.webbanhang.DTO.response.PageResponse;
import com.webbanhang.webbanhang.DTO.response.ProductDTO;
import com.webbanhang.webbanhang.Model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductRedisService {
    List<ProductModel> getAllProduct(String key) throws JsonProcessingException;

    void saveAllProduct(List<ProductModel> productModelList, String key) throws JsonProcessingException;

    ProductModel getProductByID(String id);

    String saveProduct(ProductRequestDTO productRequestDTO, MultipartFile file);

    String deleteProduct(String a);

    List<ProductModel> findCategory(String id);

    List<ProductModel> findBrand(String id);

    List<ProductModel> getProductOutOfStock();

    Page<ProductModel> getProductForPage(Integer a, String categoryID, String brandID, String sortBy, String searchQuery);

    PageResponse<List<ProductModel>> getAllProductWithSortBy(int pageNo, int pageSize, String sortBy);

    void saveGetAllProductWithSortBy(int pageNo, int pageSize, String sortBy, PageResponse<List<ProductModel>> a);

    Page<ProductDTO> searchProducts(int pageNo, int pageSize, String searchQuery, String categoryId);

    void clear();

    void clearProduct();
}
