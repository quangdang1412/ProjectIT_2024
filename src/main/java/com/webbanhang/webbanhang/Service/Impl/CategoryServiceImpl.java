package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DTO.request.Other.CategoryRequestDTO;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.CategoryModel;
import com.webbanhang.webbanhang.Repository.ICategoryRepository;
import com.webbanhang.webbanhang.Service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    @Override
    public List<CategoryModel> getAllCategory() {
        return (List<CategoryModel>) categoryRepository.findAll();
    }
    @Override
    public CategoryModel findCategoryByID(String id)
    {
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }
    @Override
    public String save(CategoryRequestDTO a) {
        try{
            CategoryModel categoryModel = CategoryModel.builder()
                    .categoryID(a.getCategoryID())
                    .categoryName(a.getCategoryName())
                    .build();
            categoryRepository.save(categoryModel);
            return categoryModel.getCategoryID();
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(e.getMessage());
            throw new CustomException(property+ " has been used");
        }
    }

}
