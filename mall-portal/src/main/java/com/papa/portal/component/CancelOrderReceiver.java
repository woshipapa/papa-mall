package com.papa.portal.component;


import com.papa.portal.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//监视死信队列
@Component
@RabbitListener(queues = {"mall.order.cancel"})
public class CancelOrderReceiver {

    private static Logger logger= LoggerFactory.getLogger(CancelOrderReceiver.class);


    @Resource
    private OmsPortalOrderService omsPortalOrderService;

    @RabbitHandler
    public void handle(Long id){
        logger.info("receive delay message orderId:{}",id);
        omsPortalOrderService.cancelOrder(id);
    }


}
