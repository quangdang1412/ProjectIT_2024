package com.webbanhang.webbanhang.Controller.api;

import com.webbanhang.webbanhang.DTO.request.Order.OrderRequestDTO;
import com.webbanhang.webbanhang.DTO.response.ResponseData;
import com.webbanhang.webbanhang.DTO.response.ResponseError;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
@Validated
@Slf4j
@RequiredArgsConstructor
public class OrderAPI {
    private final IOrderService orderService;
    private final ICartService cartService;
    private final CheckLogin login;
    private Integer number = 0;
    private Integer success = 0;

    @PostMapping("/placeOrder")
    public ResponseData<String> addOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO, HttpSession session) {
        try {
            UserModel userModel = (UserModel) session.getAttribute("UserLogin");
            String orderID = orderService.save(orderRequestDTO, userModel.getUserID());
//            String orderID = orderService.save(orderRequestDTO, "U003");
            cartService.deleteByUserCartUserID(userModel.getUserID());
            login.refreshUser(session);
            log.info("Request {} Success", success++);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Success", orderID);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            if (e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "không thể đặt hàng");
        }
    }

    @PutMapping("/update")
    public ResponseData<String> updateOrder(@RequestBody Map<String, String> allParams) {
        try {
            log.info("Request update Order: {}", allParams.get("orderID"));

            String userId = orderService.updateOrder(allParams);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Success", userId);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            if (e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update failed");
        }
    }

    @DeleteMapping("/delete/{orderID}")
    public ResponseData<String> deleteOrder(@PathVariable("orderID") String id, HttpSession session) {
        try {
            String orderID = orderService.deleteOrder(id);
            log.info("Request Delete Order: {}", orderID);
            login.refreshUser(session);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Success", orderID);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete failed");
        }
    }
}
