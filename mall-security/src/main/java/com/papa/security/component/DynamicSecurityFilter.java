package com.papa.security.component;

import com.papa.security.config.IgnoreUrlsConfig;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Resource
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;


    @Autowired
    public void setAccessDecisionManager(DynamicAccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        FilterInvocation fi=new FilterInvocation(servletRequest,servletResponse,filterChain);
        if(httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.toString())){
            //options请求会直接放行
            fi.getChain().doFilter(fi.getRequest(),fi.getResponse());
            return ;
        }
        PathMatcher pathMatcher=new AntPathMatcher();
        for(String path: ignoreUrlsConfig.getUrls()){
            if(pathMatcher.match(path,httpServletRequest.getRequestURI())){
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return ;
            }
        }
        InterceptorStatusToken token=super.beforeInvocation(fi);
        try{
            fi.getChain().doFilter(fi.getRequest(),fi.getResponse());
        }finally {
            super.afterInvocation(token,fi);
        }


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
}
