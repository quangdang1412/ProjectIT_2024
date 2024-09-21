package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.Model.OrderDetailModel;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetailModel> getAllOrderDetail();
    List<Object[]> getBestSellingProducts();


}
