package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.DiscountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiscountRepository extends JpaRepository<DiscountModel,String> {
}
