package com.papa.portal.service.impl;

import cn.hutool.db.sql.Order;
import com.papa.mbg.mapper.OmsOrderMapper;
import com.papa.mbg.model.OmsOrder;
import com.papa.portal.design.OmsPortalOrderFacade;
import com.papa.portal.design.OrderStatus;
import com.papa.portal.design.OrderStatusChangeEvent;
import com.papa.portal.domain.ConfirmOrderResult;
import com.papa.portal.domain.OrderParam;
import com.papa.portal.service.OmsPortalOrderService;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OmsPortalOrderFacade orderFacade;

    @Resource
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    @Resource
    private StateMachinePersister stateMachineMemPersister;

    @Resource
    private OmsOrderMapper orderMapper;

    @Override
    public ConfirmOrderResult generateConfirm(List<Long> ids) {
        return orderFacade.generateConfirmOrder(ids);
    }

    @Override
    public void cancelOrder(Long orderId) {
            orderFacade.cancelOrder(orderId);
    }


    public Map<String, Object> generateOrder(OrderParam orderParam){
        return orderFacade.generateOrder(orderParam);
    }

    @Override
    public void pay(Long orderId) {
        //TODO 后续加入其他逻辑,比如调用第三方支付接口
        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        logger.info("线程名称：{},尝试支付，订单号：{}" ,Thread.currentThread().getName() , orderId);
        if(!sendEvent(OrderStatusChangeEvent.PAID,order)){
            logger.error("线程名称：{},支付失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
            throw new RuntimeException("支付失败, 订单状态异常");
        }
    }

    @Override
    public void delivery(Long orderId) {

        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        logger.info("线程名称：{},尝试发货，订单号：{}" ,Thread.currentThread().getName() , orderId);
        if(!sendEvent(OrderStatusChangeEvent.DELIVERY,order)){
            logger.error("线程名称：{},发货失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
            throw new RuntimeException("发货失败, 订单状态异常");
        }
    }




    private synchronized boolean sendEvent(OrderStatusChangeEvent event, OmsOrder order){
        boolean result = false;
        try{
            orderStateMachine.start();
            stateMachineMemPersister.restore(orderStateMachine,String.valueOf(order.getId()));
            Message<OrderStatusChangeEvent> message = MessageBuilder.withPayload(event).setHeader("order", order).build();
            result = orderStateMachine.sendEvent(message);

            //持久化状态机的状态
            stateMachineMemPersister.persist(orderStateMachine,String.valueOf(order.getId()));

        }catch (Exception e){
            logger.error("订单操作失败:{}", e);
        }finally {
            orderStateMachine.stop();
        }
        return result;
    }


}
