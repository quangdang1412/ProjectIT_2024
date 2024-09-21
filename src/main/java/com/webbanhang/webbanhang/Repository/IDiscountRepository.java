package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.DiscountModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiscountRepository extends CrudRepository<DiscountModel,String> {
}
