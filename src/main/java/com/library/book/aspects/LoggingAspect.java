package com.library.book.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    //Controller katmanının tüm methodlarından önce loglama yapar
    @Before("execution(* com.library.book.controllers.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        System.out.println("Executing method: " + joinPoint.getSignature());
    }

    //Service katmanının tüm metodlarından sonra loglama yapar ve sonucu döner
    /*@AfterReturning(value = "execution(* com.library.book.services.*.*(..))", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        System.out.println("Executing method: " + joinPoint.getSignature().getName() + ", Result: " + result.toString());
    }*/

}
