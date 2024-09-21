package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.BrandModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandRepository extends CrudRepository<BrandModel,String> {
}
