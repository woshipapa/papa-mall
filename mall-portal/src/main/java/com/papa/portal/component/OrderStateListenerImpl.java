package com.papa.portal.component;

import com.papa.mbg.mapper.OmsOrderMapper;
import com.papa.mbg.model.OmsOrder;
import com.papa.portal.design.OrderStatus;
import com.papa.portal.design.OrderStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
@Slf4j
public class OrderStateListenerImpl {

    @Resource
    private OmsOrderMapper orderMapper;

    /**
     * 监听等待付款---->等待发货,触发的event是付款成功
     * @param eventMessage
     */
    @OnTransition(source = "PENDING_PAYMENT",target = "PENDING_SHIPMENT")
    public void payTransition(Message<OrderStatusChangeEvent> eventMessage){

        OmsOrder omsOrder = eventMessage.getHeaders().get("order", OmsOrder.class);
        log.info("支付，状态机反馈信息：{}",  eventMessage.getHeaders().toString());
        omsOrder.setStatus(OrderStatus.PENDING_SHIPMENT.getValue());
        orderMapper.updateByPrimaryKeySelective(omsOrder);
    }


    @OnTransition(source = "PENDING_SHIPMENT",target = "SHIPPED")
    public void deliveryTransition(Message<OrderStatusChangeEvent> eventMessage){

        OmsOrder omsOrder = eventMessage.getHeaders().get("order", OmsOrder.class);
        log.info("发货，状态机反馈信息：{}",  eventMessage.getHeaders().toString());
        omsOrder.setStatus(OrderStatus.SHIPPED.getValue());
        orderMapper.updateByPrimaryKeySelective(omsOrder);
    }

    @OnTransition(source = "SHIPPED",target = "RECEIVED")
    public void receiveTransition(Message<OrderStatusChangeEvent> eventMessage){

        OmsOrder omsOrder = eventMessage.getHeaders().get("order",OmsOrder.class);
        log.info("收货，状态机反馈信息：{}",  eventMessage.getHeaders().toString());
        omsOrder.setStatus(OrderStatus.COMPLETED.getValue());
        orderMapper.updateByPrimaryKeySelective(omsOrder);
    }
}
