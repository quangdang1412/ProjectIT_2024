package com.webbanhang.webbanhang.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.webbanhang.webbanhang.Config.PayOSConfig;
import com.webbanhang.webbanhang.DTO.request.Order.OrderRequestDTO;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.OrderDetailModel;
import com.webbanhang.webbanhang.Model.OrderModel;
import com.webbanhang.webbanhang.Model.PaymentModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Repository.IOrderRepository;

import jakarta.transaction.Transactional;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayOSPaymentService {

    private final PayOSConfig payOSConfig;
    private final IUserService userService;
    private final IOrderRepository orderRepository;



    public String createCheckout(Integer finalTotal, String orderId, String returnUrl,String cancelUrl) {
        try {
            // Initialize PayOS using the configuration
            PayOS payos = new PayOS(payOSConfig.getClientId(), payOSConfig.getApiKey(), payOSConfig.getChecksumKey());
            
            Long orderCode = System.currentTimeMillis() / 1000;

           
            String description = "Đơn hàng: " + orderId;

            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .description(description)
                    .amount(finalTotal)
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .build();

            CheckoutResponseData data = payos.createPaymentLink(paymentData);
            return data.getCheckoutUrl();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
}
