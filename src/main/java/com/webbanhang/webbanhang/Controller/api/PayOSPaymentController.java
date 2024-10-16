package com.webbanhang.webbanhang.Controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webbanhang.webbanhang.DTO.request.CheckoutRequest;
import com.webbanhang.webbanhang.Service.PayOSPaymentService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PayOSPaymentController {
    private final PayOSPaymentService payOSPaymentService;

    @PostMapping("/createcheckout")
    public ResponseEntity<String> createCheckout(@RequestBody CheckoutRequest checkoutRequest) {
        String checkoutUrl = payOSPaymentService.createCheckout(
                checkoutRequest.getFinalTotal(),
                checkoutRequest.getOrderId(),
                checkoutRequest.getReturnUrl(),
                checkoutRequest.getCancelUrl()
        );

        return ResponseEntity.ok(checkoutUrl);
    }
    
}
