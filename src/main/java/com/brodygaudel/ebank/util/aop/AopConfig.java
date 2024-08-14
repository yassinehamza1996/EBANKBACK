

package com.brodygaudel.ebank.util.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AopConfig {
    @Before("execution(* com.brodygaudel.ebank.services.implementations.CustomerServiceImpl.*(..))")
    public void logMethodCustomerServiceEntry(JoinPoint joinPoint){
        logger(joinPoint);
    }

    @Before("execution(* com.brodygaudel.ebank.services.implementations.BankAccountServiceImpl.*(..))")
    public void logMethodBankServiceEntry(JoinPoint joinPoint){
        logger(joinPoint);
    }

    @Before("execution(* com.brodygaudel.ebank.services.implementations.AccountOperationServiceImpl.*(..))")
    public void logMethodOperationServiceEntry(JoinPoint joinPoint){
        logger(joinPoint);
    }

    private void logger(@NotNull JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        log.info("In method : "+name+":");
    }
}
