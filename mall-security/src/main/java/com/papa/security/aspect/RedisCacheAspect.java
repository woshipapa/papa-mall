package com.papa.security.aspect;

import com.papa.security.annotation.CacheException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class RedisCacheAspect {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Pointcut("execution(public * com.papa.service.*CacheService.*(..))")
    public void cacheAspect(){}


    @Around(value = "cacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint){
        Object res = null;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        try{
            res = joinPoint.proceed();
        } catch (Throwable e) {
            if(method.isAnnotationPresent(CacheException.class))
                throw new RuntimeException(e);
            else{
                logger.info(e.getMessage());
            }
        }
        return res;
    }
}
