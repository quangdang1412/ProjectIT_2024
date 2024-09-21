package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.CategoryModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends CrudRepository<CategoryModel,String> {
}
