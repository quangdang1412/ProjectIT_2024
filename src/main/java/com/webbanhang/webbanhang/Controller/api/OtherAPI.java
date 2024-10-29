package com.webbanhang.webbanhang.Controller.api;

import com.webbanhang.webbanhang.DTO.request.Other.BrandRequestDTO;
import com.webbanhang.webbanhang.DTO.request.Other.CategoryRequestDTO;
import com.webbanhang.webbanhang.DTO.request.Other.DiscountRequestDTO;
import com.webbanhang.webbanhang.DTO.response.ResponseData;
import com.webbanhang.webbanhang.DTO.response.ResponseError;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Service.IBrandService;
import com.webbanhang.webbanhang.Service.ICategoryService;
import com.webbanhang.webbanhang.Service.IDiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/other")
@Validated
@Slf4j
@RequiredArgsConstructor
public class OtherAPI {
    private final ICategoryService categoryService;
    private final IBrandService brandService;
    private final IDiscountService discountService;
    @PostMapping("/addCategory")
    public ResponseData<String> addCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO)
    {
        try{
            log.info("Request add order: {}",categoryRequestDTO.getCategoryID());
            String categoryID = categoryService.save(categoryRequestDTO) ;
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",categoryID);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Save failed");
        }
    }
    @PutMapping("/updateCategory")
    public ResponseData<String> updateCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO)
    {
        try{
            log.info("Request update category: {}",categoryRequestDTO.getCategoryID());
            String categoryID = categoryService.save(categoryRequestDTO) ;
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",categoryID);

        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Save failed");
        }
    }
    @PostMapping("/addBrand")
    public ResponseData<String> addBrand( @Valid @RequestBody BrandRequestDTO brandRequestDTO)
    {
        try{

            log.info("Request add brand: {}",brandRequestDTO.getBrandID());
            String brandID = brandService.save(brandRequestDTO) ;
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",brandID);


        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Save failed");
        }
    }
    @PutMapping("/updateBrand")
    public ResponseData<String> updateBrand(@Valid @RequestBody BrandRequestDTO brandRequestDTO)
    {
        try{
             log.info("Request update brand: {}",brandRequestDTO.getBrandID());
             String brandID = brandService.save(brandRequestDTO) ;
             return new ResponseData<>(HttpStatus.CREATED.value(),"Success",brandID);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Save failed");
        }
    }
    @PostMapping("/addDiscount")
    public ResponseData<String> addDiscount(@Valid @RequestBody DiscountRequestDTO discountRequestDTO)
    {
        try{

            log.info("Request add discount: {}",discountRequestDTO);
            String discountID = discountService.save(discountRequestDTO) ;
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",discountID);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Save failed");
        }
    }
}
