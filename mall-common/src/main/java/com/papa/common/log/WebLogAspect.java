package com.papa.common.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.papa.common.domain.WebLog;
import com.papa.common.util.RequestUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.XMLEncoder;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
public class WebLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);


    @Pointcut("execution(public * com.papa.controller.*.*(..))||execution(public * com.papa.*.controller.*.*(..))")
    public void webLog(){

    }

    @Around(value = "webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        WebLog webLog = new WebLog();
        Long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long end = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if(method.isAnnotationPresent(ApiOperation.class)){
            ApiOperation annotation = method.getAnnotation(ApiOperation.class);
            webLog.setDescription(annotation.value());
        }
        webLog.setStartTime(start);
        webLog.setSpendTime((int)(end-start));
        webLog.setResult(result);
        webLog.setIp(RequestUtil.getRequestIp(request));
        webLog.setUrl(request.getRequestURL().toString());
        webLog.setUri(request.getRequestURI().toString());
        webLog.setRequestMethod(request.getMethod());
        webLog.setParameter(Arrays.toString(joinPoint.getArgs()));
        webLog.setBasePath(StrUtil.removeSuffix(request.getRequestURL(), URLUtil.url(request.getRequestURL().toString()).getPath()));
        webLog.setUserName(request.getRemoteUser());
        logger.info("{}", JSONUtil.parse(webLog).toString());
        return result;
    }





}
