package com.webbanhang.webbanhang.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbanhang.webbanhang.DTO.request.Product.ProductRequestDTO;
import com.webbanhang.webbanhang.DTO.response.PageResponse;
import com.webbanhang.webbanhang.DTO.response.ProductDTO;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.ImageModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Repository.IProductRepository;
import com.webbanhang.webbanhang.Service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final IBrandService brandService;
    private final ICategoryService categoryService;
    private final IDiscountService discountService;
    private final ISuppilerService supplierService;
    private final IImageService imageService;
    private final IProductRedisService productRedisService;

    @Override
    public List<ProductModel> getAllProduct() {
        String key = "getAllProduct";
        try {
            // Kiểm tra cache
            List<ProductModel> productModelList = productRedisService.getAllProduct(key);
            if (productModelList != null) {
                log.info("Lấy danh sách sản phẩm từ cache Redis.");
                return productModelList;
            }

            // Nếu cache không có, lấy từ DB
            productModelList = productRepository.findAll();
            productRedisService.saveAllProduct(productModelList, key);
            log.info("Dữ liệu lấy từ database và đã lưu vào cache.");
            return productModelList;
        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách sản phẩm: ", e);
            return Collections.emptyList(); // Trả về danh sách rỗng thay vì `null`
        }

    }

    @Override
    public ProductModel getProductByID(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public String saveProduct(ProductRequestDTO productRequestDTO, MultipartFile file) {
        try {

            ProductModel productModel = ProductModel.builder()
                    .productID(productRequestDTO.getProductID())
                    .productName(productRequestDTO.getProductName())
                    .brand(brandService.findBrandByID(productRequestDTO.getBrand()))
                    .category(categoryService.findCategoryByID(productRequestDTO.getCategory()))
                    .description(productRequestDTO.getDescription())
                    .discount(productRequestDTO.getDiscount().equals("empty") ? null : discountService.findDiscountByID(productRequestDTO.getDiscount()))
                    .quantity(productRequestDTO.getQuantity())
                    .unitCost(productRequestDTO.getUnitCost())
                    .unitPrice(productRequestDTO.getUnitPrice())
                    .supplier(supplierService.findSupplierByID(productRequestDTO.getSupplierID()))
                    .active(true)
                    .build();
            ImageModel imageProduct = null;
            if (!file.isEmpty()) {
                String fileName = imageService.upload(file);
                if (fileName.contains("Something went wrong"))
                    throw new CustomException("Failed");
                if (!imageService.isPresent(fileName)) {
                    imageService.addImage(fileName);
                }
                imageProduct = imageService.findOneImage(fileName);
                productModel.setImage(imageProduct);
            } else {
                productModel.setImage(productRepository.findById(productRequestDTO.getProductID()).get().getImage());
            }
            productRepository.save(productModel);
            return productModel.getProductID();
        } catch (Exception e) {
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".") + 1, error.lastIndexOf("]"));
            log.info(error);
            throw new CustomException(property + " has been used");
        }
    }

    @Override
    public String deleteProduct(String id) {
        try {
            ProductModel productModel = getProductByID(id);
            productModel.setActive(!productModel.isActive());
            productRepository.save(productModel);
            return productModel.getProductID();
        } catch (Exception e) {
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".") + 1, error.lastIndexOf("]"));
            log.info(error);
            throw new CustomException(property + " has been used");
        }
    }

    @Override
    public List<ProductModel> findCategory(String id) {
        return productRepository.findProductModelsByCategoryCategoryID(id);
    }

    @Override
    public List<ProductModel> findBrand(String id) {
        return productRepository.findByBrand(id);
    }

    @Override
    public List<ProductModel> getProductOutOfStock() {
        return productRepository.getProductOutOfStock();
    }

    @Override
    public Page<ProductModel> getProductForPage(Integer pageNumber, String categoryID, String brandID, String sortBy, String searchQuery) {
        Pageable pageable = null;
        if (sortBy == null)
            pageable = PageRequest.of(pageNumber - 1, 9);

        else {
            if (sortBy.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageNumber - 1, 9, Sort.by(Sort.Direction.ASC, "unitPrice"));
            } else {
                pageable = PageRequest.of(pageNumber - 1, 9, Sort.by(Sort.Direction.DESC, "unitPrice"));
            }
        }

        return productRepository.getProductForPage(
                categoryID != null ? "%" + categoryID + "%" : null,
                brandID != null ? "%" + brandID + "%" : null,
                searchQuery != null ? "%" + searchQuery + "%" : null,
                pageable
        );
    }

    @Override
    public PageResponse<List<ProductModel>> getAllProductWithSortBy(int pageNo, int pageSize, String sortBy) {
        if (pageNo > 0)
            pageNo--;
        String substring = sortBy.substring(1);
        sortBy = String.valueOf(sortBy.charAt(0)).toUpperCase() + substring;
        List<Sort.Order> sorts = new ArrayList<>();
        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorts));

        Page<ProductModel> productPage = productRepository.findAll(pageable);
        PageResponse<List<ProductModel>> a = productRedisService.getAllProductWithSortBy(pageNo + 1, pageSize, sortBy);
        if (a != null)
            return a;
        List<ProductModel> list = productPage.stream().map(product -> ProductModel.builder()
                        .productID(product.getProductID())
                        .brand(product.getBrand())
                        .category(product.getCategory())
                        .image(product.getImage())
                        .description(product.getDescription())
                        .discount(product.getDiscount())
                        .unitPrice(product.getUnitPrice())
                        .build())
                .toList();
        a = PageResponse.<List<ProductModel>>builder()
                .pageNo(++pageNo)
                .pageSize(pageSize)
                .totalPage(productPage.getTotalPages())
                .items(list)
                .build();
        productRedisService.saveGetAllProductWithSortBy(pageNo, pageSize, sortBy, a);
        return a;
    }

    public Page<ProductDTO> searchProducts(int pageNo, int pageSize, String searchQuery, String categoryId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        Page<ProductModel> productModels;

        if (categoryId != null) {
            productModels = productRepository.findByCategory_categoryID(categoryId, pageable);
        } else {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                productModels = productRepository.findByProductNameContainingIgnoreCase(searchQuery, pageable);
            } else {
                productModels = productRepository.findAll(pageable);
            }
        }

        return productModels.map(this::convertToDTO);
    }

    private ProductDTO convertToDTO(ProductModel productModel) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductID(productModel.getProductID());
        productDTO.setProductName(productModel.getProductName());
        productDTO.setBrand(productModel.getBrand());
        productDTO.setCategory(productModel.getCategory());
        productDTO.setDiscount(productModel.getDiscount());
        productDTO.setImage(productModel.getImage());
        productDTO.setDescription(productModel.getDescription());
        productDTO.setUnitPrice(productModel.getUnitPrice());
        productDTO.setQuantity(productModel.getQuantity());
        productDTO.setActive(productModel.isActive());
        productDTO.setUnitCost(productModel.getUnitCost());
        return productDTO;
    }

}
