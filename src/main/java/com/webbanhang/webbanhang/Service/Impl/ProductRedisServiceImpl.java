package com.webbanhang.webbanhang.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbanhang.webbanhang.DTO.request.Product.ProductRequestDTO;
import com.webbanhang.webbanhang.DTO.response.PageResponse;
import com.webbanhang.webbanhang.DTO.response.ProductDTO;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Service.IProductRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRedisServiceImpl implements IProductRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public List<ProductModel> getAllProduct(String key) {
        try {
            log.info("Đang lấy dữ liệu từ cache Redis...");
            String json = (String) redisTemplate.opsForValue().get(key);
            return json != null ? objectMapper.readValue(json, new TypeReference<List<ProductModel>>() {
            }) : null;
        } catch (Exception e) {
            log.error("Lỗi khi đọc dữ liệu từ Redis: ", e);
            return null;
        }
    }

    @Override
    public void saveAllProduct(List<ProductModel> productList, String key) {
        try {
            String json = objectMapper.writeValueAsString(productList);
            redisTemplate.opsForValue().set(key, json, Duration.ofHours(1)); // Lưu cache trong 1 giờ
            log.info("Dữ liệu đã được lưu vào Redis với key: {}", key);
        } catch (Exception e) {
            log.error("Lỗi khi lưu dữ liệu vào Redis: ", e);
        }
    }

    @Override
    public ProductModel getProductByID(String id) {
        return null;
    }

    @Override
    public String saveProduct(ProductRequestDTO productRequestDTO, MultipartFile file) {
        return null;
    }

    @Override
    public String deleteProduct(String a) {
        return null;
    }

    @Override
    public List<ProductModel> findCategory(String id) {
        return null;
    }

    @Override
    public List<ProductModel> findBrand(String id) {
        return null;
    }

    @Override
    public List<ProductModel> getProductOutOfStock() {
        return null;
    }

    @Override
    public Page<ProductModel> getProductForPage(Integer a, String categoryID, String brandID, String sortBy, String searchQuery) {
        return null;
    }

    @Override
    public PageResponse<List<ProductModel>> getAllProductWithSortBy(int pageNo, int pageSize, String sortBy) {
        String key = String.format("getAllProductWithSortBy:%d:%d:%s", pageNo, pageSize, sortBy);
        try {
            log.info("Đang lấy dữ liệu từ cache Redis...");
            String json = (String) redisTemplate.opsForValue().get(key);
            return json != null ? objectMapper.readValue(json, new TypeReference<PageResponse<List<ProductModel>>>() {
            }) : null;
        } catch (Exception e) {
            log.error("Lỗi khi đọc dữ liệu từ Redis: ", e);
            return null;
        }
    }

    @Override
    public void saveGetAllProductWithSortBy(int pageNo, int pageSize, String sortBy, PageResponse<List<ProductModel>> list) {
        String key = String.format("getAllProductWithSortBy:%d:%d:%s", pageNo, pageSize, sortBy);
        try {
            String json = objectMapper.writeValueAsString(list);
            redisTemplate.opsForValue().set(key, json, Duration.ofHours(1));
            log.info("Dữ liệu đã được lưu vào Redis với key: {}", key);
        } catch (Exception e) {
            log.error("Lỗi khi lưu dữ liệu vào Redis: ", e);
        }
    }

    @Override
    public Page<ProductDTO> searchProducts(int pageNo, int pageSize, String searchQuery, String categoryId) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void clearProduct() {
        try {
            Set<String> keys = redisTemplate.keys("*Product*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.info("Đã xóa toàn bộ cache Redis chứa 'product'");
            } else {
                log.info("Không có cache nào chứa 'product' để xóa");
            }
        } catch (Exception e) {
            log.error("Lỗi khi xóa cache Redis: {}", e.getMessage(), e);
        }
    }
}
