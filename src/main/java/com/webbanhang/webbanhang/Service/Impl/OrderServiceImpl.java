package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DTO.request.Order.OrderRequestDTO;
import com.webbanhang.webbanhang.DTO.response.DashboardResponse;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Model.PK.UserCouponID;
import com.webbanhang.webbanhang.Repository.ICouponRepository;
import com.webbanhang.webbanhang.Repository.IOrderRepository;
import com.webbanhang.webbanhang.Repository.IUserCouponRepository;
import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements IOrderService {

    private final IUserService userService;
    private final IOrderRepository orderRepository;
    private final ICouponRepository couponRepository;
    private final IUserCouponRepository userCouponRepository;
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
            if(a.getShippingOption().equals("50000"))
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
            if(!a.getCouponID().isEmpty()){
                deleteUserCoupon(order.getUserOrder(),a.getCouponID());
            }
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
            if(order.getStatus().equals("Hoàn thành") && order.getPaymentModel().getStatus().equals("Đã thanh toán"))
                applyCouponBasedOnTotalPrice(order,order.getTotalPrice());
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
    @Override
    public boolean updateStatus(String orderId) {
        Optional<OrderModel> optionalOrder = orderRepository.findById(orderId);
        
        if (optionalOrder.isPresent()) {
            OrderModel order = optionalOrder.get();
    
            PaymentModel payment = order.getPaymentModel();
            if (payment == null) {
                payment = new PaymentModel();
                order.setPaymentModel(payment);// Set lại payment cho order
            }
    
            payment.setStatus("Đã thanh toán");
    
            orderRepository.save(order);
            return true;
        }
    
        return false;
    }
    @Override
    public DashboardResponse dataChart(LocalDate startDate,LocalDate endDate){
        // chart Area
        List<OrderModel> listOrder = orderRepository.revenue(startDate, endDate);
        Map<String, Double> totalRevenueByDate = new TreeMap<>();
        long numberDays = ChronoUnit.DAYS.between(startDate, endDate);
        for (OrderModel orderModel : listOrder) {
            String orderDateStr;
            LocalDate orderDate = orderModel.getOrderDate().toLocalDate();

            if (numberDays <= 1) {
                orderDateStr = orderModel.getOrderDate().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } else if (numberDays <= 30) {
                orderDateStr = orderDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } else if (numberDays <= 92) {
                int week = orderDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
                orderDateStr = "Week " + week + ", " + orderDate.getYear();
            } else if (numberDays <= 365*2) {
                orderDateStr = orderDate.format(DateTimeFormatter.ofPattern("MM-yyyy"));
            } else {
                orderDateStr = orderDate.format(DateTimeFormatter.ofPattern("yyyy"));
            }
            double totalAmount = orderModel.getTotalPrice() + orderModel.getTransportFee();
            totalRevenueByDate.put(orderDateStr, totalRevenueByDate.getOrDefault(orderDateStr, 0.0) + totalAmount);
        }
        List<String> time = new ArrayList<>(totalRevenueByDate.keySet());
        List<String> data = totalRevenueByDate.values().stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        // chart Pie
        List<String> top5 = new ArrayList<>();
        List<String> dataTop5 = new ArrayList<>();
        List<Object[]> listProduct = orderRepository.topSeller(startDate, endDate);
        for(int i=0;i<Math.min(5, listProduct.size());i++){
            Object[] productInfo = listProduct.get(i);
            if (productInfo != null && productInfo.length > 0) {
                top5.add(String.valueOf(productInfo[0]));
                dataTop5.add(String.valueOf(productInfo[1]));
            }
        }
        return DashboardResponse.builder()
                .time(time)
                .data(data)
                .top5(top5)
                .dataTop5(dataTop5)
                .build();
    }

    @Override
    public Integer orderNeedToProcess() {
        return orderRepository.countOrderNeedToProcess();
    }

    @Override
    public Double revenueTotal() {
        return orderRepository.revenueTotal();
    }

    @Override
    public Double revenuePerMonth() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.of(endDate.getYear(),endDate.getMonth(),1);
        return orderRepository.revenuePerMonth(startDate,endDate);
    }

    @Override
    public Double profitTotal() {
        return orderRepository.profitTotal();
    }

    public void applyCouponBasedOnTotalPrice(OrderModel order,Double totalPrice) {
        UserModel user = order.getUserOrder();

        if (totalPrice > 100000 && totalPrice < 300000) {
            applyOrUpdateUserCoupon(user, "Cp1");
        } else if (totalPrice >= 300000 && totalPrice < 500000) {
            applyOrUpdateUserCoupon(user, "Cp2");
        } else if (totalPrice >= 500000 && totalPrice < 1000000) {
            applyOrUpdateUserCoupon(user, "Cp3");
        } else if (totalPrice >= 1000000) {
            applyOrUpdateUserCoupon(user, "Cp4");
        }
    }
    private void applyOrUpdateUserCoupon(UserModel user, String couponCode) {
        CouponModel coupon = couponRepository.findById(couponCode).orElseThrow(() ->
                new NoSuchElementException("Coupon not found with code: " + couponCode)
        );

        UserCouponID userCouponID = new UserCouponID(user,coupon);
        Optional<UserCouponModel> userCouponModel = userCouponRepository.findById(userCouponID);

        if (userCouponModel.isPresent()) {
            // Nếu đã tồn tại, tăng số lượng quantity lên 1
            UserCouponModel existingCoupon = userCouponModel.get();
            existingCoupon.setQuantity(existingCoupon.getQuantity() + 1);
            userCouponRepository.save(existingCoupon);
        } else {
            // Nếu chưa tồn tại, tạo mới bản ghi với quantity = 1
            UserCouponModel newUserCoupon = UserCouponModel.builder()
                    .userCoupon(user)
                    .couponUser(coupon)
                    .quantity(1)
                    .build();

            userCouponRepository.save(newUserCoupon);
        }
    }
    private void deleteUserCoupon(UserModel user, String couponCode){
        CouponModel coupon = couponRepository.findById(couponCode).orElseThrow(() ->
                new NoSuchElementException("Coupon not found with code: " + couponCode)
        );
        UserCouponID userCouponID = new UserCouponID(user,coupon);
        Optional<UserCouponModel> userCouponModel = userCouponRepository.findById(userCouponID);
        if (userCouponModel.isPresent()) {
            UserCouponModel existingCoupon = userCouponModel.get();
            existingCoupon.setQuantity(existingCoupon.getQuantity()-1);
            if(existingCoupon.getQuantity() == 0)
                userCouponRepository.delete(existingCoupon);
            userCouponRepository.save(existingCoupon);
        }
    }
}
