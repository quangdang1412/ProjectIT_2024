package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.Model.CouponModel;
import com.webbanhang.webbanhang.Repository.ICouponRepository;
import com.webbanhang.webbanhang.Service.ICouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class CouponServiceImpl implements ICouponService {
    private  final ICouponRepository couponRepository;
    @Override
    public String save(CouponModel a) {
        couponRepository.save(a);
        return a.getCouponID();
    }

    @Override
    public String delete(String id) {
        CouponModel a = couponRepository.findById(id).get();
        a.setActive(!a.isActive());
        couponRepository.save(a);
        return a.getCouponID();
    }

    @Override
    public List<CouponModel> getAllCoupon() {
        return couponRepository.findAll();
    }

    @Override
    public CouponModel findCouponByID(String id) {
        return couponRepository.findById(id).get();
    }
}
