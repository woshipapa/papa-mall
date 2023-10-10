package com.papa.security.component;

import cn.hutool.core.util.URLUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String,ConfigAttribute> configAttributeMap = null;

    @Resource
    private DynamicSecurityService dynamicSecurityService;


    @PostConstruct
    public void loadDataSource(){configAttributeMap = dynamicSecurityService.loadDataSource();}


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if(configAttributeMap == null) this.loadDataSource();
        List<ConfigAttribute> configAttributeList=new ArrayList<>();
        FilterInvocation filterInvocation=(FilterInvocation) o;
        String requestUrl= filterInvocation.getRequestUrl();
        String path= URLUtil.getPath(requestUrl);//可以得到uri部分
        PathMatcher pathMatcher=new AntPathMatcher();
        Iterator<String> iterator=configAttributeMap.keySet().iterator();
        while(iterator.hasNext()){
            String pattern=iterator.next();
            if(pathMatcher.match(path,pattern)){
                configAttributeList.add(configAttributeMap.get(pattern));
            }
        }
        return configAttributeList;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
