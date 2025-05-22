package com.example.transaccionservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String TRANSFERENCIAS_QUEUE = "transferencias.queue";
    public static final String TRANSFERENCIAS_EXCHANGE = "transferencias.exchange";
    public static final String TRANSFERENCIAS_ROUTING_KEY = "transferencias.routingkey";

    @Bean
    public Queue queue() {
        return new Queue(TRANSFERENCIAS_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(TRANSFERENCIAS_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TRANSFERENCIAS_ROUTING_KEY);
    }
}