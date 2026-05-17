package com.energy_expense_tracker.user_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.energy_expense_tracker.user_service.controller..*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        log.info("Controller: {} called with args: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @Before("execution(* com.energy_expense_tracker.user_service.service..*(..))")
    public void logBeforeService(JoinPoint joinPoint) {
        log.info("Service: {} called with args: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.energy_expense_tracker.user_service.service..*(..))", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result) {
        log.info("Service: {} returned: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    @AfterThrowing(pointcut = "execution(* com.energy_expense_tracker.user_service..*(..))", throwing = "ex")
    public void logAfterException(JoinPoint joinPoint, Exception ex) {
        log.error("Exception in {}: {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage());
    }
}
