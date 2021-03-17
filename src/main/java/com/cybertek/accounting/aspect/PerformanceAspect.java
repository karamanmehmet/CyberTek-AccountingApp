package com.cybertek.accounting.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;


@Aspect
@Configuration
@Slf4j
public class PerformanceAspect {

    @Pointcut("execution(* com.cybertek.accounting.controller.*.*(..))")
    private void anyControllerOperation(){}

    @Around("anyControllerOperation()")
    public Object performanceAdvice(ProceedingJoinPoint proceedingJoinPoint){

        long operationStartTime = System.currentTimeMillis();

        Object result=null;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long operationStopTime = System.currentTimeMillis();

        log.info("Time taken to execute: {} ms, Method : {} - Parameters : {} ",
                (operationStopTime-operationStartTime),
                proceedingJoinPoint.getSignature().toShortString(),
                proceedingJoinPoint.getArgs());

        return result;
    }



}
