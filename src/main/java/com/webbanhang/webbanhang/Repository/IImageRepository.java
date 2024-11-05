package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.DiscountModel;
import com.webbanhang.webbanhang.Model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepository extends JpaRepository<ImageModel,String> {
    ImageModel findByImageCode(String id);
}
