package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.Order.OrderRequestDTO;
import com.webbanhang.webbanhang.Model.OrderModel;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    List<OrderModel> getAllOrder();
    String save(OrderRequestDTO a,String id);
    String updateOrder(Map<String,String> allParams);
    String deleteOrder(String id);
    List<OrderModel> getOrderByStatus(String id);
    OrderModel getOrderByID(String id);
    boolean updateStatus(String id);
}
