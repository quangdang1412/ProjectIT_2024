package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;

@Service
@RequiredArgsConstructor
public class PayOSPaymentService {

    @Value("${payos.client-id}")
    private String clientId;

    @Value("${payos.api-key}")
    private String apiKey;

    @Value("${payos.checksum-key}")
    private String checksumKey;

    public String createCheckout(Integer finalTotal, String orderId, String returnUrl,String cancelUrl) {
        try {

            PayOS payos = new PayOS(clientId,apiKey,checksumKey);
            
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
