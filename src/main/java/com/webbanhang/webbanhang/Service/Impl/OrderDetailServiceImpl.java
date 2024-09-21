package com.webbanhang.webbanhang.Service.Impl;

import java.util.List;

import com.webbanhang.webbanhang.DAO.IOrderDetailDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.webbanhang.webbanhang.Model.OrderDetailModel;
import com.webbanhang.webbanhang.Service.IOrderDetailService;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {
    private final IOrderDetailDAO orderDetailDAO;

    @Override
    public List<OrderDetailModel> getAllOrderDetail() {
        return orderDetailDAO.getAllOrderDetail();
    }

    @Override
    public List<Object[]> getBestSellingProducts() {
        return orderDetailDAO.getBestSellingProducts();
    }
    
}
