package com.bxacosta.accountservice.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String CLIENT_EXCHANGE_NAME = "client.direct";

    public static final String CLIENT_STATUS_QUEUE_NAME = "client.status";
    public static final String CLIENT_RPC_QUEUE_NAME = "client.rpc";


    @Bean
    public Jackson2JsonMessageConverter converter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setNullAsOptionalEmpty(true);
        return converter;
    }

    @Bean
    public DirectExchange clientDirectExchange() {
        return new DirectExchange(CLIENT_EXCHANGE_NAME);
    }

    @Bean
    public Queue clientStatusQueue() {
        return new Queue(CLIENT_STATUS_QUEUE_NAME);
    }

    @Bean
    public Queue clientRequestQueue() {
        return new Queue(CLIENT_RPC_QUEUE_NAME);
    }
}