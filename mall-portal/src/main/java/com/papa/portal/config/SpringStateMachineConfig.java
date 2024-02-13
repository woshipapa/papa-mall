package com.papa.portal.config;

import com.papa.portal.design.OrderStatus;
import com.papa.portal.design.OrderStatusChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine(name = "orderStateMachine")
@Scope(value = "prototype")
public class SpringStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 这里配置了有哪些状态和初始状态
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states.withStates()
                .initial(OrderStatus.PENDING_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    /**
     * 这里配置原状态到目标状态的转换，以及触发的事件
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(OrderStatus.PENDING_PAYMENT)
                .target(OrderStatus.PENDING_SHIPMENT)
                .event(OrderStatusChangeEvent.PAID)
                .and()
                .withExternal()
                .source(OrderStatus.PENDING_SHIPMENT)
                .target(OrderStatus.SHIPPED)
                .event(OrderStatusChangeEvent.DELIVERY)
                .and()
                .withExternal()
                .source(OrderStatus.SHIPPED)
                .target(OrderStatus.COMPLETED)
                .event(OrderStatusChangeEvent.RECEIVED)
                .and()
                .withExternal()
                .source(OrderStatus.PENDING_PAYMENT)
                .target(OrderStatus.CANCELED)
                .event(OrderStatusChangeEvent.CANCEL)
                .and()
                .withExternal()
                .source(OrderStatus.PENDING_SHIPMENT)
                .target(OrderStatus.CANCELED)
                .event(OrderStatusChangeEvent.CANCEL)
                .and()
                .withExternal()
                ;

        }
}
