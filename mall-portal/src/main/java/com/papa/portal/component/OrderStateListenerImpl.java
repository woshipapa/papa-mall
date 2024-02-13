package com.papa.portal.component;

import cn.hutool.db.sql.Order;
import com.papa.mbg.mapper.OmsOrderMapper;
import com.papa.mbg.mapper.OmsOrderStatusChangeMapper;
import com.papa.mbg.model.OmsOrder;
import com.papa.mbg.model.OmsOrderItem;
import com.papa.mbg.model.OmsOrderStatusChange;
import com.papa.portal.dao.PortalOrderDAO;
import com.papa.portal.design.OrderStatus;
import com.papa.portal.design.OrderStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
@Slf4j
public class OrderStateListenerImpl {

    @Resource
    private OmsOrderMapper orderMapper;

    @Resource
    private OmsOrderStatusChangeMapper statusChangeMapper;


    @Resource
    private PortalOrderDAO orderDAO;
    /**
     * 监听等待付款---->等待发货,触发的event是付款成功
     * @param eventMessage
     */
    @OnTransition(source = "PENDING_PAYMENT",target = "PENDING_SHIPMENT")
    @Transactional
    public void payTransition(Message<OrderStatusChangeEvent> eventMessage){

        OmsOrder omsOrder = eventMessage.getHeaders().get("order", OmsOrder.class);
        log.info("支付，状态机反馈信息：{}",  eventMessage.getHeaders().toString());

        //插入订单状态修改的历史记录
        String oldStatus = OrderStatus.PENDING_PAYMENT.getMessage();
        String newStatus = OrderStatus.PENDING_SHIPMENT.getMessage();
        OmsOrderStatusChange orderStatusChange = new OmsOrderStatusChange();
        orderStatusChange.setOrderId(omsOrder.getId());
        orderStatusChange.setChangeAt(new Date());
        orderStatusChange.setPreviousStatus(oldStatus);
        orderStatusChange.setCurrentStatus(newStatus);
        statusChangeMapper.insertSelective(orderStatusChange);
        //修改订单表中的订单状态
        omsOrder.setStatus(OrderStatus.PENDING_SHIPMENT.getValue());
        orderMapper.updateByPrimaryKeySelective(omsOrder);

        //支付成功后，将释放锁定库存和实际库存进行扣减
        List<OmsOrderItem> orderItems = orderDAO.getOrderItems(omsOrder.getId());
        orderDAO.updateSkuStock(orderItems);
    }


    @OnTransition(source = "PENDING_SHIPMENT",target = "SHIPPED")
    public void deliveryTransition(Message<OrderStatusChangeEvent> eventMessage){

        OmsOrder omsOrder = eventMessage.getHeaders().get("order", OmsOrder.class);
        log.info("发货，状态机反馈信息：{}",  eventMessage.getHeaders().toString());

        String oldStatus = OrderStatus.PENDING_SHIPMENT.getMessage();
        String newStatus = OrderStatus.SHIPPED.getMessage();
        OmsOrderStatusChange orderStatusChange = new OmsOrderStatusChange();
        orderStatusChange.setOrderId(omsOrder.getId());
        orderStatusChange.setChangeAt(new Date());
        orderStatusChange.setPreviousStatus(oldStatus);
        orderStatusChange.setCurrentStatus(newStatus);
        statusChangeMapper.insertSelective(orderStatusChange);



        omsOrder.setStatus(OrderStatus.SHIPPED.getValue());
        orderMapper.updateByPrimaryKeySelective(omsOrder);
    }

    @OnTransition(source = "SHIPPED",target = "RECEIVED")
    public void receiveTransition(Message<OrderStatusChangeEvent> eventMessage){

        OmsOrder omsOrder = eventMessage.getHeaders().get("order",OmsOrder.class);
        log.info("收货，状态机反馈信息：{}",  eventMessage.getHeaders().toString());

        String oldStatus = OrderStatus.SHIPPED.getMessage();
        String newStatus = OrderStatus.COMPLETED.getMessage();
        OmsOrderStatusChange orderStatusChange = new OmsOrderStatusChange();
        orderStatusChange.setOrderId(omsOrder.getId());
        orderStatusChange.setChangeAt(new Date());
        orderStatusChange.setPreviousStatus(oldStatus);
        orderStatusChange.setCurrentStatus(newStatus);
        statusChangeMapper.insertSelective(orderStatusChange);


        omsOrder.setStatus(OrderStatus.COMPLETED.getValue());
        omsOrder.setConfirmStatus(1);
        omsOrder.setModifyTime(new Date());
        orderMapper.updateByPrimaryKeySelective(omsOrder);
    }
}
