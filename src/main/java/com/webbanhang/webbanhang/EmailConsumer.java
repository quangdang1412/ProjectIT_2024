package com.webbanhang.webbanhang;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbanhang.webbanhang.Constraint.JobQueue;
import com.webbanhang.webbanhang.DTO.request.EmailRequest;
import com.webbanhang.webbanhang.DTO.request.ResetPasswordRequest;
import com.webbanhang.webbanhang.Service.Impl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final MailService mailService;

    @RabbitListener(queues = JobQueue.QUEUE_SEND_EMAIL)
    public void receiveMessage(byte[] message) {
        try {
            String json = new String(message);
            ObjectMapper objectMapper = new ObjectMapper();
            EmailRequest emailRequest = objectMapper.readValue(json, EmailRequest.class);
            mailService.sendUpdateOrderMail(emailRequest.getOrder(), emailRequest.getCheckChangeStatus());
        } catch (Exception e) {
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
        }

    }

    @RabbitListener(queues = JobQueue.QUEUE_SEND_EMAIL_RESET_PASS)
    public void receiveMessageRestPassword(byte[] message) {
        try {
            String json = new String(message);
            ObjectMapper objectMapper = new ObjectMapper();
            ResetPasswordRequest request = objectMapper.readValue(json, ResetPasswordRequest.class);

            mailService.sendResetPasswordMail(request.getConfirmLink(), request.getEmail());
        } catch (Exception e) {
            System.err.println("Lỗi khi xử lý tin nhắn: " + e.getMessage());
        }
    }

}
