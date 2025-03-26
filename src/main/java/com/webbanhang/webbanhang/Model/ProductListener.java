package com.webbanhang.webbanhang.Model;

import com.webbanhang.webbanhang.Service.IProductRedisService;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductListener {

    private final IProductRedisService productRedisService;

    @PrePersist
    public void prePersist(ProductModel productModel) {
        log.info("PrePersist");
    }

    @PostPersist
    public void postPersist(ProductModel productModel) {
        log.info("PostPersist - Xóa cache sản phẩm");
        productRedisService.clearProduct();
    }

    @PreUpdate
    public void preUpdate(ProductModel productModel) {
        log.info("PreUpdate");
    }

    @PostUpdate
    public void postUpdate(ProductModel productModel) {
        log.info("PostUpdate - Xóa cache sản phẩm");
        productRedisService.clearProduct();
    }

    @PreRemove
    public void preRemove(ProductModel productModel) {
        log.info("PreRemove");
    }

    @PostRemove
    public void postRemove(ProductModel productModel) {
        log.info("PostRemove - Xóa cache sản phẩm");
        productRedisService.clearProduct();
    }
}
