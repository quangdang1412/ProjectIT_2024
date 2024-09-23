package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DAO.IProductDAO;
import com.webbanhang.webbanhang.DTO.response.PageResponse;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Repository.IProductRepository;
import com.webbanhang.webbanhang.Service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductDAO productDAO;
    private final IProductRepository productRepository;

    @Override
    public List<ProductModel> getAllProduct() {
        return productDAO.getAllProduct();
    }

    @Override
    public ProductModel findOneProduct(String id) {
        return productDAO.findOneProduct(id);
    }

    @Override
    public boolean addProduct(ProductModel a) {
        return productDAO.addProduct(a);
    }

    @Override
    public boolean updateProduct(ProductModel a) {
        return productDAO.updateProduct(a);
    }

    @Override
    public boolean deleteProduct(ProductModel a) {
        return productDAO.deleteProduct(a);
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
    public Page<ProductModel> getProductForPage(Integer a) {
        return productDAO.getProductForPage(a);
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
