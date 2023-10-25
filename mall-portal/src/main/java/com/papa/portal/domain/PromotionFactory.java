package com.papa.portal.domain;

import io.swagger.models.auth.In;

import java.util.HashMap;
import java.util.Map;

public class PromotionFactory {
    enum promotionType{
        SINGLE(1,"单品促销"),
        LADDER(3,"折扣"),
        FULL_REDUCE(4,"满减")
        ;
        private Integer type;
        private String message;
         promotionType(Integer type,String message){
            this.type = type;
            this.message = message;
        }

        public Integer getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }

    private static Map<Integer,PromotionStrategy> map = new HashMap<>();
    static {
        map.put(1,new SinglePromotion());
        map.put(3,new LadderPromotion());
        map.put(4,new FullReductionPromotionStrategy());
    }

    public static PromotionStrategy getPromotionStrategy(Integer type){
        return map.get(type);
    }

}
