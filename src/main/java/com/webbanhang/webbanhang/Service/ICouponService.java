package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.Other.DiscountRequestDTO;
import com.webbanhang.webbanhang.Model.CouponModel;
import com.webbanhang.webbanhang.Model.DiscountModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICouponService {
    String save(CouponModel a);
    String delete(CouponModel a);
    List<CouponModel> getAllCoupon();
    CouponModel findCouponByID(String id);

}
