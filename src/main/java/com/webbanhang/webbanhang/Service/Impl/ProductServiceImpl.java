package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DAO.IProductDAO;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductDAO productDAO;


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


}
