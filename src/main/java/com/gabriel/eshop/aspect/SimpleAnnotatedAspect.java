package com.gabriel.eshop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SimpleAnnotatedAspect {

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {

    }

    @Before("anyPublicOperation()")
    public void call() throws Throwable {
        System.out.println("Hello from annotated aspect!");
    }
}
