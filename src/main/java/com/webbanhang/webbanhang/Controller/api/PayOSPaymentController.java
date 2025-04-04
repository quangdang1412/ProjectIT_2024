package com.webbanhang.webbanhang.Controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webbanhang.webbanhang.DTO.request.CheckoutRequest;
import com.webbanhang.webbanhang.Service.PayOSPaymentService;
import com.webbanhang.webbanhang.Service.Impl.OrderServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PayOSPaymentController {
    private final PayOSPaymentService payOSPaymentService;
    private final OrderServiceImpl orderService;

    @PostMapping("/createcheckout")
    public ResponseEntity<String> createCheckout(@RequestBody CheckoutRequest checkoutRequest, HttpSession session) {
        String checkoutUrl = payOSPaymentService.createCheckout(
                checkoutRequest.getFinalTotal(),
                checkoutRequest.getOrderId(),
                checkoutRequest.getReturnUrl(),
                checkoutRequest.getCancelUrl()
        );
        String checkoutId = checkoutUrl.substring(checkoutUrl.lastIndexOf('/') + 1);
        session.setAttribute("id", checkoutId);
        return ResponseEntity.ok(checkoutUrl);
    }

    @PutMapping("/updatePaymentStatus/{orderId}")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable String orderId, HttpSession session) {
        if (session.getAttribute("id") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
        boolean isUpdated = orderService.updateStatus(orderId);
        if (isUpdated) {
            return ResponseEntity.ok("Order updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update order");
        }
    }
}
