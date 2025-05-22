package com.example.transaccionservice.mensajeria;

import com.example.transaccionservice.dto.TransaccionDTO;
import com.example.transaccionservice.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTransaccionInterbancaria(TransaccionDTO dto) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.TRANSFERENCIAS_EXCHANGE,
                RabbitConfig.TRANSFERENCIAS_ROUTING_KEY,
                dto
        );
    }
}