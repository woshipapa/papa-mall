package com.papa.portal.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.HashMap;
import java.util.Map;



@Configuration
@Slf4j
public class Persist<E,S> {


    @Bean(name = "stateMachineMemPersister")
    public static StateMachinePersister getPersister(){
        return new DefaultStateMachinePersister(
                new StateMachinePersist() {

                    private Map map = new HashMap<>();
                    @Override
                    public void write(StateMachineContext stateMachineContext, Object o) throws Exception {
                        log.info("持久化状态机,context:{},contextObj:{}", JSONUtil.toJsonStr(stateMachineContext), JSONUtil.toJsonStr(o));
                        map.put(o,stateMachineContext);
                    }

                    @Override
                    public StateMachineContext read(Object o) throws Exception {
                        log.info("获取状态机,contextObj:{}", JSONUtil.toJsonStr(o));
                        StateMachineContext context = (StateMachineContext) map.get(o);
                        log.info("获取状态机结果,stateMachineContext:{}", JSONUtil.toJsonStr(context));
                        return context;
                    }
                }
        );
    }
}
