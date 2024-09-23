package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DTO.request.Other.BrandRequestDTO;
import com.webbanhang.webbanhang.Exception.DuplicateException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.BrandModel;
import com.webbanhang.webbanhang.Repository.IBrandRepository;
import com.webbanhang.webbanhang.Service.IBrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements IBrandService {

    private final IBrandRepository brandRepository;
    @Override
    public List<BrandModel> getAllBrand() {
        return (List<BrandModel>) brandRepository.findAll();
    }
    @Override
    public BrandModel findBrandByID(String id){
        return brandRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Brand not found"));
    }

    @Override
    public String save(BrandRequestDTO a) {
        try{
            BrandModel brandModel = BrandModel.builder()
                    .brandID(a.getBrandID())
                    .brandName(a.getBrandName())
                    .build();
            brandRepository.save(brandModel);
            return brandModel.getBrandID();
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(e.getMessage());
            throw new DuplicateException(property+ " has been used");
        }
    }

}
