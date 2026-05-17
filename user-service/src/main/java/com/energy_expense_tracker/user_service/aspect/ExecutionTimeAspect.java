package com.energy_expense_tracker.user_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    @Around("execution(* com.energy_expense_tracker.user_service.controller..*(..)) || execution(* com.energy_expense_tracker.user_service.service..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        log.info("{} executed in {} ms",
                joinPoint.getSignature().toShortString(),
                duration);

        return result;
    }
}
