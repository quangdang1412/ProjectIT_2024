package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryModel, String> {
}
