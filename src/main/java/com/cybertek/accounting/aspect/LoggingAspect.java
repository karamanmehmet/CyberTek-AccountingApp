package com.cybertek.accounting.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.cybertek.accounting.controller.*.*(..))")
    private void anyControllerOperation(){}

    @Before("anyControllerOperation()")
    public void beforeControllerOperationAdvice(JoinPoint joinPoint){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Before(User: {} Method : {} - Parameters : {}", auth.getName(),
                joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }


}
