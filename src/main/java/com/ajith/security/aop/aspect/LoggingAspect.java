package com.ajith.security.aop.aspect;

import com.ajith.security.aop.audit.AuditData;
import com.ajith.security.aop.audit.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final AuditService auditService;
    @Pointcut("execution(* com.ajith.security.user.controllers..*.*(..)) || execution(* com.ajith.security.auth.AuthenticationController.*(..)) ||execution(* com.ajith.security.roles.controller..*.*(..)) ||execution(* com.ajith.security.admin.controller..*.*(..))")
    public void myPointCut() {

    }


    @Around("myPointCut()")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String methodType = request.getMethod();
        String requestTime = LocalDateTime.now().toString();

        Object result = joinPoint.proceed();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "Anonymous";


       AuditData data = createAuditData ( joinPoint, methodType, requestURI, result, username, requestTime );
       auditService.saveAuditData ( data);

        return result;
    }

    private static AuditData createAuditData (ProceedingJoinPoint joinPoint, String methodType, String requestURI, Object result, String username, String requestTime) {
        AuditData auditData = new AuditData();
        auditData.setMethodType( methodType );
        auditData.setApiEndpoint( requestURI );
        auditData.setRequest(Arrays.toString( joinPoint.getArgs()));
        auditData.setResponse( result != null ? result.toString() : "null");
        auditData.setUserInvoked( username );
        auditData.setRequestTime( requestTime );
        return auditData;
    }


}
