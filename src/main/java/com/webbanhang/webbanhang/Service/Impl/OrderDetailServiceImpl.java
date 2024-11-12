package com.webbanhang.webbanhang.Service.Impl;

import java.util.List;

import com.webbanhang.webbanhang.Repository.IOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.webbanhang.webbanhang.Model.OrderDetailModel;
import com.webbanhang.webbanhang.Service.IOrderDetailService;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {
    private final IOrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetailModel> getAllOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public List<Object[]> getBestSellingProducts() {
        return orderDetailRepository.getBestSellingProducts();
    }

}
