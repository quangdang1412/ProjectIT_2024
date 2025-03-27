package com.webbanhang.webbanhang.Service.Impl;

import com.google.api.client.util.ArrayMap;
import com.webbanhang.webbanhang.Constraint.JobQueue;
import com.webbanhang.webbanhang.DTO.request.EmailRequest;
import com.webbanhang.webbanhang.DTO.request.Order.OrderEmailDTO;
import com.webbanhang.webbanhang.DTO.request.Order.OrderRequestDTO;
import com.webbanhang.webbanhang.DTO.response.DashboardResponse;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Repository.ICouponRepository;
import com.webbanhang.webbanhang.Repository.IOrderRepository;
import com.webbanhang.webbanhang.Repository.IProductRepository;
import com.webbanhang.webbanhang.Repository.IUserRepository;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Service.IUserService;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements IOrderService {

    private final IUserService userService;
    private final IUserRepository userRepository;
    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ICartService cartService;

    private final Lock lock = new ReentrantLock();

    @Override
    public List<OrderModel> getAllOrder() {
        return orderRepository.findAll();
    }


    @Override
    @Transactional
    public String save(OrderRequestDTO a, String id) {
        try {
            UserModel userModel = userRepository.getReferenceById(id);
            double totalPrice = 0;
            OrderModel order = OrderModel.builder()
                    .userOrder(userModel)
                    .orderID(a.getOrderID())
                    .name(a.getName())
                    .address(a.getAddress())
                    .phone(a.getPhone())
                    .build();
            PaymentModel payment = new PaymentModel();
            LocalDate today = LocalDate.now();
            LocalDate fastDay = today.plusDays(4);
            LocalDate normalDay = today.plusDays(8);
            if (a.getShippingOption().equals("50000")) {
                order.setDeliveryTime(Date.valueOf(normalDay));
                order.setTransportFee(50000.0);
            } else {
                order.setDeliveryTime(Date.valueOf(fastDay));
                order.setTransportFee(100000.0);
            }
            payment.setOrderPayment(order);
            if (!a.getPaymentMethod().equals("InPlace")) {
                payment.setMethod("Online Banking");
                payment.setStatus("Chưa thanh toán");
                order.setStatus("Chờ thanh toán");
            } else {
                payment.setMethod("Thanh toán khi nhận hàng");
                payment.setStatus("Chưa thanh toán");
                order.setStatus("Chờ xác nhận");
            }
            order.setPaymentModel(payment);
            List<OrderDetailModel> orderDetailModels = new ArrayList<>();
            List<CartModel> cartModelList = userModel.getUserCart();
            if (!cartModelList.isEmpty()) {
                for (CartModel cart : cartModelList) {
                    ProductModel productModel = productRepository.findById(cart.getProductCart().getProductID()).get();
                    if (productModel.getQuantity() < cart.getQuantity())
                        throw new CustomException("Sản phẩm " + productModel.getProductName() + " không đủ số lượng");
                    if (productModel.getDiscount() != null) {
                        totalPrice += productModel.getUnitPrice() * (100 - productModel.getDiscount().getPercentage()) / 100 * cart.getQuantity();
                    } else {
                        totalPrice += productModel.getUnitPrice() * cart.getQuantity();
                    }
                    productModel.setQuantity(productModel.getQuantity() - cart.getQuantity());
                    productModel = productRepository.save(productModel);
                    orderDetailModels.add(new OrderDetailModel(order, productModel, cart.getQuantity()));
                }
            } else {
                throw new CustomException("Vui lòng thêm sản phẩm vào giỏ hàng");
            }
            order.setOrderDetails(orderDetailModels);
            order.setTotalPrice(totalPrice);
            order.setOrderDate(Date.valueOf(LocalDate.now()));
            orderRepository.save(order);
            return order.getOrderID();
        } catch (Exception e) {
            if (e instanceof CustomException) {
                throw new CustomException(e.getMessage() != null ? e.getMessage() : "Có lỗi xảy ra");
            } else {
                log.error("Lỗi không xác định: ", e);
                throw new CustomException("Đã có lỗi xảy ra");
            }
        }
    }

    @Override
    public String updateOrder(Map<String, String> allParams) {
        try {
            OrderModel order = getOrderByID(allParams.get("orderID"));
            int checkChangeStatus = 0;
            if (!order.getStatus().equals(allParams.get("status"))) {
                order.setSellerOrder(userService.findUserByID(allParams.get("sellerID")));
                order.setStatus((allParams.get("status")));
                checkChangeStatus = 1;
            }
            order.setAddress(allParams.get("address"));
            orderRepository.save(order);
            OrderEmailDTO orderEmailDTO = new OrderEmailDTO(order.getOrderID(), order.getPhone(), order.getOrderDetails(), order.getAddress(), order.getStatus(), order.getUserOrder().getEmail());
            rabbitTemplate.convertAndSend(JobQueue.QUEUE_SEND_EMAIL, new EmailRequest(orderEmailDTO, checkChangeStatus));
            return order.getOrderID();
        } catch (Exception e) {
            if (e instanceof CustomException) {
                throw new CustomException(e.getMessage() != null ? e.getMessage() : "Có lỗi xảy ra");
            } else {
                log.error("Lỗi không xác định: ", e);
                throw new CustomException("Đã có lỗi xảy ra");
            }
        }

    }

    @Override
    public String deleteOrder(String a) {
        try {
            OrderModel orderModel = getOrderByID(a);
            for (OrderDetailModel od : orderModel.getOrderDetails()) {
                ProductModel productModel = productRepository.findById(od.getProductOrder().getProductID()).get();
                productModel.setQuantity(productModel.getQuantity() + od.getQuantity());
                productRepository.save(productModel);
            }
            orderRepository.deleteOrder(a);
            return a;
        } catch (Exception e) {
            if (e instanceof CustomException) {
                throw new CustomException(e.getMessage() != null ? e.getMessage() : "Có lỗi xảy ra");
            } else {
                log.error("Lỗi không xác định: ", e);
                throw new CustomException("Đã có lỗi xảy ra");
            }
        }
    }

    @Override
    public List<OrderModel> getOrderByStatus(String status) {
        if (status.equals("Chờ thanh toán")) {
            List<OrderModel> listOrder = orderRepository.getOrderByStatus(status);
            for (OrderModel a : listOrder) {
                expiredOrder(a);
            }
        }
        return orderRepository.getOrderByStatus(status);
    }

    @Override
    public void expiredOrder(OrderModel a) {
        LocalDateTime today = LocalDateTime.now();
        Duration duration = Duration.between(a.getCreated_at(), today);
        if (duration.toHours() > 1) {
            deleteOrder(a.getOrderID());
        }
        orderRepository.save(a);
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
                order.setPaymentModel(payment);
            }
            order.setStatus("Chờ xác nhận");
            payment.setStatus("Đã thanh toán");

            orderRepository.save(order);
            return true;
        }

        return false;
    }


    @Override
    public DashboardResponse dataChart(LocalDate startDate, LocalDate endDate) {
        // chart Area
        List<OrderModel> listOrder = orderRepository.revenue(startDate, endDate);
        Map<String, Double> totalRevenueByDate = new ArrayMap<>();
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
            } else if (numberDays <= 365 * 2) {
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
        for (int i = 0; i < Math.min(5, listProduct.size()); i++) {
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
        LocalDate startDate = LocalDate.of(endDate.getYear(), endDate.getMonth(), 1);
        return orderRepository.revenuePerMonth(startDate, endDate);
    }

    @Override
    public Double profitTotal() {
        return orderRepository.profitTotal();
    }

}
