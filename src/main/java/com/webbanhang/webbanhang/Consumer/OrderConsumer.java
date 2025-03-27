package com.webbanhang.webbanhang.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbanhang.webbanhang.Constraint.JobQueue;
import com.webbanhang.webbanhang.DTO.request.EmailRequest;
import com.webbanhang.webbanhang.DTO.request.OrderRequestRabbitDTO;
import com.webbanhang.webbanhang.Service.ICartService;
import com.webbanhang.webbanhang.Service.IOrderService;
import com.webbanhang.webbanhang.Service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final IOrderService orderService;
    private final ICartService cartService;

    private int dem = 0;

    @RabbitListener(queues = JobQueue.QUEUE_PLACE_ORDER)
    public void receiveMessage(byte[] message) {
        try {
            String json = new String(message);
            ObjectMapper objectMapper = new ObjectMapper();
            OrderRequestRabbitDTO orderRequestRabbitDTO = objectMapper.readValue(json, OrderRequestRabbitDTO.class);
            String orderID = orderService.save(orderRequestRabbitDTO.getOrderRequestDTO(), orderRequestRabbitDTO.getUserId());
//            cartService.deleteByUserCartUserID(orderRequestRabbitDTO.getUserId());
            messagingTemplate.convertAndSend("/topic/order-status", "Success");

            log.warn("Thanh Cong {}", dem++);
        } catch (Exception e) {
            messagingTemplate.convertAndSend("/topic/order-status/", "Fail");
            log.info("That bai{}", dem++);
        }

    }
}
