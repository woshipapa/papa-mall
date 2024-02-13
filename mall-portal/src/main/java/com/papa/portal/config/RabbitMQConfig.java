package com.papa.portal.config;


import com.papa.portal.domain.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange orderDirect(){
        return (DirectExchange) ExchangeBuilder.directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange()).durable(true).build();
    }

    /**
     * 延迟队列绑定的交换机
     * @return
     */
    @Bean
    public DirectExchange orderTtlDirect(){
        return (DirectExchange) ExchangeBuilder.directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange()).durable(true).build();
    }

    /**
     * 正常队列在信息过期后会带有路由发往死信的交换机
     * @return
     */
    @Bean
    public Queue orderTtlQueue(){
        Map<String,Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange",QueueEnum.QUEUE_ORDER_CANCEL.getExchange());
        args.put("x-dead-letter-routing-key",QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
        return QueueBuilder.durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getQueueName())
                .withArguments(args).build();
    }


    @Bean
    public Queue orderQueue(){
        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getQueueName());
    }


    @Bean
    public Binding orderTtlBinding(DirectExchange orderTtlDirect,Queue orderTtlQueue){
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirect)
                .with(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
    }


    @Bean
    public Binding orderBinding(DirectExchange orderDirect,Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }
}
