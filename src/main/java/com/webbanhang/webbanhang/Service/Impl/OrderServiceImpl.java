package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DTO.request.Order.OrderRequestDTO;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Repository.IOrderRepository;
import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements IOrderService {

    private final IUserService userService;
    private final IOrderRepository orderRepository;
    private final MailService mailService;

    @Override
    public List<OrderModel> getAllOrder() {
        return (List<OrderModel>) orderRepository.findAll();
    }

    @Override
    public String save(OrderRequestDTO a,String id) {
        try{
            UserModel userModel = userService.findUserByID(id);
            double totalPrice = 0;
            OrderModel order = OrderModel.builder()
                    .orderID(a.getOrderID())
                    .userOrder(userModel)
                    .name(a.getName())
                    .address(a.getAddress())
                    .phone(a.getPhone())
                    .status("Chờ xác nhận")
                    .build();
            PaymentModel payment = new PaymentModel();
            LocalDate today = LocalDate.now();
            LocalDate fastDay = today.plusDays(4);
            LocalDate normalDay = today.plusDays(8);
            if(a.getPaymentMethod().equals("50000"))
            {
                order.setDeliveryTime(Date.valueOf(normalDay));
                order.setTransportFee(50000.0);
            }
            else
            {
                order.setDeliveryTime(Date.valueOf(fastDay));
                order.setTransportFee(100000.0);
            }

            order.setOrderDate(Date.valueOf(today));
            payment.setOrderPayment(order);
            if(!a.getPaymentMethod().equals("InPlace"))
            {
                payment.setMethod("Online Banking");
                payment.setStatus("Chưa thanh toán");
            }
            else
            {
                payment.setMethod("Thanh toán khi nhận hàng");
                payment.setStatus("Chưa thanh toán");
            }
            order.setPaymentModel(payment);
            List<OrderDetailModel> orderDetailModels = new ArrayList<>();
            if(userModel.getUserCart()!= null)
            {
                for (CartModel cart : userModel.getUserCart()) {
                    if (cart.getProductCart().getDiscount() != null) {
                        totalPrice += cart.getProductCart().getUnitPrice() * (100 - cart.getProductCart().getDiscount().getPercentage()) / 100 * cart.getQuantity();
                    } else {
                        totalPrice += cart.getProductCart().getUnitPrice() * cart.getQuantity();
                    }
                    orderDetailModels.add(new OrderDetailModel(order,cart.getProductCart(), cart.getQuantity()));
                }
            }
            order.setOrderDetails(orderDetailModels);
            order.setTotalPrice(totalPrice);
            orderRepository.save(order);
            return order.getOrderID();
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(error);
            throw new CustomException(property+ " has been used");
        }
    }

    @Override
    public String updateOrder(Map<String,String> allParams) {
        try{
            OrderModel order = getOrderByID(allParams.get("orderID"));
            int checkChangeStatus = 0;
            if(!order.getStatus().equals(allParams.get("status"))){
                order.setSellerOrder(userService.findUserByID(allParams.get("sellerID")));
                order.setShipperOrder(userService.findUserByID(allParams.get("shipperID")));
                order.setStatus((allParams.get("status")));
                checkChangeStatus = 1;
            }
            order.setAddress(allParams.get("address"));
            orderRepository.save(order);
            if(checkChangeStatus == 1)
                mailService.sendUpdateOrderMail(order,checkChangeStatus);
            else
                mailService.sendUpdateOrderMail(order,checkChangeStatus);
            return order.getOrderID();
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(error);
            throw new CustomException(property+ " has been used");
        }

    }

    @Override
    public String deleteOrder(String a) {
        try{
            OrderModel orderModel =getOrderByID(a);
            orderRepository.deleteOrder(a);
            return a;
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(e.getMessage());
            throw new CustomException(property+ " has been used");
        }
    }

    @Override
    public List<OrderModel> getOrderByStatus(String status) {
        return orderRepository.getOrderByStatus(status);
    }

    @Override
    public OrderModel getOrderByID(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

}
