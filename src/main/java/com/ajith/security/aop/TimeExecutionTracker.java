package com.ajith.security.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TimeExecutionTracker {

    Logger logger = LoggerFactory.getLogger(TimeExecutionTracker.class);
    @Around ( "@annotation (com.ajith.security.aop.annotations.TaskExecutionTime)" )
    public Object trackTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis ( );
        Object obj = proceedingJoinPoint.proceed ( );
        long endTime = System.currentTimeMillis ( );
        logger.info ( "Method invoked  :" + proceedingJoinPoint.getSignature ( ) + "  time taken to finish execution  :" + (endTime - startTime) );
        return obj;
    }
}
