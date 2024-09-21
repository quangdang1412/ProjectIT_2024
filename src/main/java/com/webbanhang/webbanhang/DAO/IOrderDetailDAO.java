package com.webbanhang.webbanhang.DAO;

import com.webbanhang.webbanhang.Model.OrderDetailModel;

import java.util.List;

public interface IOrderDetailDAO {
    List<OrderDetailModel> getAllOrderDetail();
    List<Object[]> getBestSellingProducts();
}
