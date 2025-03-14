package org.example.first_hometask.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@Aspect
public class LoggingAspect {
  private int counter = 0;

  @Before("execution( * org.example.first_hometask.controller.*.*( .. ))")
  public void logBefore(JoinPoint joinPoint) {
    System.out.println("Вызвано перед методом: " + joinPoint.getSignature().getName());
    counter++;
  }

  @Around("execution( * org.example.first_hometask.controller.*.*( .. ))")
  public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Instant startTime = Instant.now();
    Object result = joinPoint.proceed();
    Instant endTime = Instant.now();
    System.out.println("Время исполнения метода " + joinPoint.getSignature().getName() + " равно " +
        Duration.between(startTime, endTime).toMillis() + " ms");
    counter++;
    return result;
  }

  public int getCounter() {
    return counter;
  }
}