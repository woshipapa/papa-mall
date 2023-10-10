package com.papa.security.component;

import cn.hutool.core.collection.CollUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public class DynamicAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if(CollUtil.isEmpty(collection)) return ;//接口没有配置权限直接放行
        Iterator<ConfigAttribute> iterator= collection.iterator();
        while(iterator.hasNext()){
            ConfigAttribute configAttribute=iterator.next();
            //将需要访问的资源和当前登录的用户拥有的资源权限比对
            String needAuthority= configAttribute.getAttribute();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for(GrantedAuthority authority:authorities){
                if(needAuthority.trim().equals(authority.getAuthority())){
                    return ;
                }
            }
        }
        throw new AccessDeniedException("抱歉,你没有访问权限");



    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
