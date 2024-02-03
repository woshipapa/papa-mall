package com.papa.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.db.sql.Order;
import com.papa.mbg.mapper.OmsOrderMapper;
import com.papa.mbg.mapper.OmsOrderSettingMapper;
import com.papa.mbg.mapper.SmsCouponHistoryMapper;
import com.papa.mbg.model.*;
import com.papa.portal.dao.PortalOrderDAO;
import com.papa.portal.design.OmsPortalOrderFacade;
import com.papa.portal.design.OrderStatus;
import com.papa.portal.design.OrderStatusChangeEvent;
import com.papa.portal.domain.ConfirmOrderResult;
import com.papa.portal.domain.OmsOrderDetail;
import com.papa.portal.domain.OrderParam;
import com.papa.portal.service.OmsPortalOrderService;
import com.papa.portal.service.UmsMemberService;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UmsMemberService memberService;

    @Resource
    private OmsOrderSettingMapper orderSettingMapper;

    @Resource
    private OmsPortalOrderFacade orderFacade;

    @Resource
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    @Resource
    private StateMachinePersister stateMachineMemPersister;

    @Resource
    private OmsOrderMapper orderMapper;

    @Resource
    private PortalOrderDAO orderDAO;

    @Resource
    private SmsCouponHistoryMapper couponHistoryMapper;

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
    public void cancelTimeOutOrder() {
        OmsOrderSetting setting = orderSettingMapper.selectByPrimaryKey(1L);
        Integer normalOrderOvertime = setting.getNormalOrderOvertime();
        List<OmsOrderDetail> timeOutOrders = orderDAO.getTimeOutOrders(normalOrderOvertime);
        if(CollUtil.isEmpty(timeOutOrders)) return ;
        List<Long> ids = timeOutOrders.stream().map(it->it.getId()).collect(Collectors.toList());
        //批量更新订单状态
        orderDAO.updateStatus(ids,OrderStatus.CLOSED.getValue());
        for(OmsOrderDetail orderDetail : timeOutOrders){
            //恢复订单商品中的sku锁定库存
            orderDAO.releaseSkuStockLock(orderDetail.getOrderItemList());
            //归还优惠券
            updateCouponStatus(orderDetail.getCouponId(),orderDetail.getMemberId(),0);
            //归还会员的积分
            if(orderDetail.getIntegration()!=null){
                UmsMember member = memberService.getById(orderDetail.getMemberId());
                memberService.updateIntegration(member.getId(),member.getIntegration()+orderDetail.getIntegration());
            }
        }
    }

    private int updateCouponStatus(Long couponId,Long memberId,Integer useStatus){
        Integer count = 0;
        SmsCouponHistoryExample couponHistoryExample = new SmsCouponHistoryExample();
        couponHistoryExample.createCriteria().andUseStatusEqualTo(useStatus==0?1:0).andMemberIdEqualTo(memberId)
                .andCouponIdEqualTo(couponId);
        List<SmsCouponHistory> couponHistories = couponHistoryMapper.selectByExample(couponHistoryExample);
        if(!CollUtil.isEmpty(couponHistories)){
            SmsCouponHistory smsCouponHistory = couponHistories.get(0);
            smsCouponHistory.setUseTime(null);
            if(useStatus == 1) smsCouponHistory.setUseTime(new Date());
            smsCouponHistory.setUseStatus(useStatus);
            count = couponHistoryMapper.updateByPrimaryKeySelective(smsCouponHistory);
        }
        return count;
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
