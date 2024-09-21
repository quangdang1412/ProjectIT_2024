package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.Other.DiscountRequestDTO;
import com.webbanhang.webbanhang.Model.DiscountModel;

import java.util.List;

public interface IDiscountService {
    List<DiscountModel> getAllDiscount();
    DiscountModel findDiscountByID(String id);
    String save(DiscountRequestDTO a);
}
