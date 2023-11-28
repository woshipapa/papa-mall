package com.papa.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.papa.mbg.mapper.OmsCartItemMapper;
import com.papa.mbg.model.OmsCartItem;
import com.papa.mbg.model.OmsCartItemExample;
import com.papa.mbg.model.UmsMember;
import com.papa.portal.domain.CartPromotionItem;
import com.papa.portal.service.OmsCartItemService;
import com.papa.portal.service.OmsPromotionService;
import com.papa.portal.service.UmsMemberService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OmsCartItemServiceImpl implements OmsCartItemService {

    @Resource
    private OmsCartItemMapper cartItemMapper;

    @Resource
    private UmsMemberService memberService;
    @Override
    public int add(OmsCartItem cartItem) {
        UmsMember member = memberService.getCurrentMember();
        //给添加的商品添加当前登录会员的信息
        cartItem.setCreateDate(new Date());
        cartItem.setDeleteStatus(0);
        cartItem.setMemberId(member.getId());
        cartItem.setMemberNickname(member.getNickname());
        OmsCartItem existCartItem = findCartItem(cartItem);
        if(existCartItem!=null){
            //已经存在相同商品，直接添加数量，并设置修改日期
            existCartItem.setQuantity(existCartItem.getQuantity()+cartItem.getQuantity());
            existCartItem.setModifyDate(new Date());
            return cartItemMapper.updateByPrimaryKeySelective(existCartItem);
        }else{
            //不存在相同商品，直接添加
            return cartItemMapper.insertSelective(cartItem);
        }

    }

    /**
     * 查询出当前用户的购物车列表
     * @param memberId
     * @return
     */
    @Override
    public List<OmsCartItem> list(Long memberId) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId).andDeleteStatusEqualTo(0);
        return cartItemMapper.selectByExample(example);
    }

    /**
     * 根据会员id，商品id，商品的sku_id寻找购物车中是否有相同的商品
     * @param cartItem
     * @return
     */

    private OmsCartItem findCartItem(OmsCartItem cartItem){
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(cartItem.getMemberId());
        criteria.andProductIdEqualTo(cartItem.getProductId());
        criteria.andDeleteStatusEqualTo(0);
        if(cartItem.getProductSkuId()!=null){
            //有的商品可能不分具体的sku
            criteria.andProductSkuIdEqualTo(cartItem.getProductSkuId());
        }
        List<OmsCartItem> omsCartItemList = cartItemMapper.selectByExample(example);
        if(CollUtil.isNotEmpty(omsCartItemList)){
            return omsCartItemList.get(0);
        }
        return null;
    }
    @Resource
    private OmsPromotionService promotionService;
    public List<CartPromotionItem> listPromotion(Long memberId,List<Long> cartIds){
        List<OmsCartItem> cartItems = list(memberId);
        //获取到了选中的购物车中的商品,id---->cartItem
        if(CollUtil.isNotEmpty(cartItems))
            cartItems= cartItems.stream().filter(it -> cartIds.contains(it.getId())).collect(Collectors.toList());
        List<CartPromotionItem> cartPromotionItems = promotionService.calCartPromotion(cartItems);
        return cartPromotionItems;
    }

    /**
     * 这里使用逻辑删除购物车中的商品
     * @param memberId
     * @param ids
     * @return
     */
    @Override
    public int delete(Long memberId,List<Long> ids) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(memberId).andIdIn(ids);
        OmsCartItem item = new OmsCartItem();
        item.setDeleteStatus(1);
        return cartItemMapper.updateByExampleSelective(item,example);
    }

    @Override
    public int updateQuantity(Long memberId, Long cartItemId, Integer count) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId).andDeleteStatusEqualTo(0).andIdEqualTo(cartItemId);
        OmsCartItem item = new OmsCartItem();
        item.setQuantity(count);
        return cartItemMapper.updateByExampleSelective(item,example);
    }

    @Override
    public int clear(Long memberId) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        OmsCartItem item = new OmsCartItem();
        item.setDeleteStatus(1);
        return cartItemMapper.updateByExampleSelective(item,example);
    }


}
