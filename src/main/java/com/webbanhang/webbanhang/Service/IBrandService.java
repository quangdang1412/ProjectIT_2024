package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.Other.BrandRequestDTO;
import com.webbanhang.webbanhang.Model.BrandModel;

import java.util.List;

public interface IBrandService {
    List<BrandModel> getAllBrand();

    BrandModel findBrandByID(String id);

    String save(BrandRequestDTO a);

}
