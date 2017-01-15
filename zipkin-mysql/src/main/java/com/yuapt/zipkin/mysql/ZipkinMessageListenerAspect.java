package com.yuapt.zipkin.mysql;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by jimmy on 16/12/28.
 */
@Aspect
@Component
public class ZipkinMessageListenerAspect {

    @Pointcut("execution(public * org.springframework.cloud.sleuth.zipkin.stream.ZipkinMessageListener.sink(..))")
    public void point() {
    }

    @Before("point()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
       // System.out.println(JSON.toJSONString(args));

    }
    @AfterReturning(returning = "ret", pointcut = "point()")
    public void doAfterReturning(Object ret) throws Throwable {
    }
}
