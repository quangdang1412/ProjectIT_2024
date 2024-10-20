package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DAO.IProductDAO;
import com.webbanhang.webbanhang.DTO.request.Product.ProductRequestDTO;
import com.webbanhang.webbanhang.DTO.response.PageResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final IProductDAO productDAO;
    private final IProductRepository productRepository;
    private final IBrandService brandService;
    private final ICategoryService categoryService;
    private final IDiscountService discountService;
    private final ISuppilerService supplierService;
    private final IImageService imageService;

    @Override
    public List<ProductModel> getAllProduct() {
        return productDAO.getAllProduct();
    }

    @Override
    public ProductModel getProductByID(String id) {
        return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found"));
    }

    @Override
    public String saveProduct(ProductRequestDTO productRequestDTO, MultipartFile file) {
        try{

            ProductModel productModel= ProductModel.builder()
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
                    .deleteProduct(1)
                    .build();
            ImageModel imageProduct = null;
            if(!file.isEmpty()){
                String fileName = imageService.upload(file);
                if(fileName.contains("Something went wrong"))
                    throw new CustomException("Failed");
                if(imageService.findOneImage(fileName)==null)
                {
                    imageService.addImage(fileName);
                }
                imageProduct = imageService.findOneImage(fileName);
                productModel.setImage(imageProduct);
            }else{
                productModel.setImage(productRepository.findById(productRequestDTO.getProductID()).get().getImage());
            }
            productRepository.save(productModel);
            return productModel.getProductID();
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(error);
            throw new CustomException(property+ " has been used");
        }
    }
    @Override
    public String deleteProduct(String id) {
        try{
            ProductModel productModel = getProductByID(id);
            productModel.setDeleteProduct(0);
            productRepository.save(productModel);
            return productModel.getProductID();
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(error);
            throw new CustomException(property+ " has been used");
        }
    }

    @Override
    public List<ProductModel> findCategory(String id) {
        return productDAO.findCategory(id);
    }

    @Override
    public Page<ProductModel> findCategoryForPage(String id,Integer a) {
       return productDAO.findCategoryForPage(id,a);
    }

    @Override
    public Page<ProductModel> getProductForPage(Integer a, String categoryID, String brandID, String sortBy) {
        return productDAO.getProductForPage(a,categoryID,brandID,sortBy);
    }

    @Override
    public PageResponse<?> getAllProductWithSortBy(int pageNo, int pageSize, String sortBy) {
        if(pageNo>0)
            pageNo--;
        String substring = sortBy.substring(1);
        sortBy = String.valueOf(sortBy.charAt(0)).toUpperCase()+substring;
        List<Sort.Order> sorts=new ArrayList<>();
        if(StringUtils.hasLength(sortBy)){
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    sorts.add(new Sort.Order(Sort.Direction.ASC,matcher.group(1)));
                }
                else{
                    sorts.add(new Sort.Order(Sort.Direction.DESC,matcher.group(1)));
                }
            }
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sorts));
        Page<ProductModel> productPage = productRepository.findAll(pageable);
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
        return PageResponse.builder()
                .pageNo(++pageNo)
                .pageSize(pageSize)
                .totalPage(productPage.getTotalPages())
                .items(list)
                .build();
    }
}
