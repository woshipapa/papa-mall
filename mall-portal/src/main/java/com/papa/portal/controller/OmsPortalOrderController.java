package com.papa.portal.controller;

import com.papa.common.api.CommonResult;
import com.papa.portal.domain.OrderParam;
import com.papa.portal.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
@Api(tags = "OmsPortalOrderController",description = "订单管理")
public class OmsPortalOrderController {

    @Resource
    private OmsPortalOrderService orderService;


    @RequestMapping(value = {"/generateOrder","/create"},method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("根据购物车信息生成订单")
    public CommonResult generateOrder(@RequestBody OrderParam param){
        Map<String,Object> result = orderService.generateOrder(param);
        return CommonResult.success(result,"下单成功");
    }


    @RequestMapping(value = "/pay/{orderId}",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("调用第三方支付接口进行支付")
    public CommonResult pay(@PathVariable("orderId") Long id){
        orderService.pay(id);
        return CommonResult.success(1);
    }


    @RequestMapping(value = "/delivery/{orderId}")
    @ResponseBody
    @ApiOperation("发货")
    public CommonResult delivery(@PathVariable("orderId") Long id){
        orderService.delivery(id);
        return CommonResult.success(1);
    }


}
