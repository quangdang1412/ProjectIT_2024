package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.Model.OrderModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.from}")
    private String emailFrom;
    private final SpringTemplateEngine templateEngine;
    public void sendUpdateOrderMail(OrderModel orderModel,int checkChangeStatus) throws MessagingException, UnsupportedEncodingException {
        log.info("Sending Confirm Link.....");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        Context context = new Context();
        Map<String,Object> properties = new HashMap<>();
        properties.put("orderID",orderModel.getOrderID());
        properties.put("phoneNumber",orderModel.getPhone());
        properties.put("carts",orderModel.getOrderDetails());
        properties.put("address",orderModel.getAddress());
        if (checkChangeStatus == 1) {
            if (orderModel.getStatus().equals("Đã xác nhận"))
                properties.put("textInformation", "Đơn hàng của bạn đã được xác nhận và xử lý. Bạn sẽ nhận đươc thông báo từ chúng tôi nếu có sự thay đổi về đơn hàng. ");
            else if (orderModel.getStatus().equals("Đang giao"))
                properties.put("textInformation", "Đơn hàng của bạn Đang được giao. Đơn hàng của bạn sẽ sớm được giao thôiii");
            else
                properties.put("textInformation", "Đơn hàng của bạn đã bị hủy");
        } else {
            properties.put("textInformation", "Đơn hàng của bạn đã được cập nhật thông tin. Bạn sẽ nhận đươc thông báo từ chúng tôi nếu có sự thay đổi về đơn hàng. ");
        }
        context.setVariables(properties);
        helper.setFrom(emailFrom,"TapHoaIT");
        helper.setTo(orderModel.getUserOrder().getEmail());
        helper.setSubject("Thông tin đơn hàng TapHoaIT");
        try {
            String html = templateEngine.process("ConfirmMail", context);
            helper.setText(html, true);
            javaMailSender.send(message);
            log.info("Sent successfully .....");
        } catch (Exception e) {
            log.error("Error while sending email: " + e.getMessage(), e);
        }
    }
    public void sendResetPasswordMail(String url,String email) throws MessagingException, UnsupportedEncodingException {
        log.info("Sending Confirm Link.....");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        Context context = new Context();
        Map<String,Object> properties = new HashMap<>();
        properties.put("urlReset",url);
        context.setVariables(properties);
        helper.setFrom(emailFrom,"TapHoaIT");
        helper.setTo(email);
        helper.setSubject("Reset mật khẩu TapHoaIT");
        try {
            String html = templateEngine.process("ResetPasswordMail", context);
            helper.setText(html, true);
            javaMailSender.send(message);
            log.info("Sent successfully .....");
        } catch (Exception e) {
            log.error("Error while sending email: " + e.getMessage(), e);
        }
    }
}
