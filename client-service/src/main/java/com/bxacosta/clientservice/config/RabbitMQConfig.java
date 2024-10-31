package com.bxacosta.clientservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CLIENT_EXCHANGE_NAME = "client.direct";

    public static final String CLIENT_STATUS_QUEUE_NAME = "client.status";
    public static final String CLIENT_STATUS_UPDATE_ROUTING_KEY = "client.status.update";

    public static final String CLIENT_RPC_QUEUE_NAME = "client.rpc";
    public static final String CLIENT_RPC_REPORT_ROUTING_KEY = "client.rpc.report";

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

    @Bean
    public Binding clientStatusUpdateBinding(DirectExchange clientDirectExchange, Queue clientStatusQueue) {
        return BindingBuilder.bind(clientStatusQueue).to(clientDirectExchange).with(CLIENT_STATUS_UPDATE_ROUTING_KEY);
    }

    @Bean
    public Binding clientRequestRPCBinding(DirectExchange clientDirectExchange, Queue clientRequestQueue) {
        return BindingBuilder.bind(clientRequestQueue).to(clientDirectExchange).with(CLIENT_RPC_REPORT_ROUTING_KEY);
    }
}