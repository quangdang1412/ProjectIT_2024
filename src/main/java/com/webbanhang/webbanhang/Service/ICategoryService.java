package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.Other.CategoryRequestDTO;
import com.webbanhang.webbanhang.Model.CategoryModel;

import java.util.List;

public interface ICategoryService {
    List<CategoryModel> getAllCategory();
    CategoryModel findCategoryByID(String id);
    String save(CategoryRequestDTO a);
}
