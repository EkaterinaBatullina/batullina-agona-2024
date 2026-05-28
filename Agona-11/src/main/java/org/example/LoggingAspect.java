package org.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(Loggable)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = 0;
        String methodName = joinPoint.getSignature().getName();

        Class<?> targetClass = joinPoint.getTarget().getClass();

        if (targetClass.isAnnotationPresent(RestController.class) || targetClass.isAnnotationPresent(Controller.class)) {
            logger.info("Request to controller - Method: {} | Parameters: {}", methodName, joinPoint.getArgs());
        }

        if (targetClass.isAnnotationPresent(Service.class)) {
            logger.debug("Service method - Method: {} | Parameters: {}", methodName, joinPoint.getArgs());
        }

        if (targetClass.isAnnotationPresent(Repository.class)) {
            logger.debug("Repository method - Method: {} | Parameters: {}", methodName, joinPoint.getArgs());
            startTime = System.currentTimeMillis();
        }

        Object result = joinPoint.proceed();

        if (targetClass.isAnnotationPresent(RestController.class) || targetClass.isAnnotationPresent(Controller.class)) {
            logger.info("Response from controller - Method: {} | Return Value: {}", methodName, result);
        }

        if (targetClass.isAnnotationPresent(Service.class)) {
            logger.debug("Service method - Method: {} | Return Value: {}", methodName, result);
        }

        if (targetClass.isAnnotationPresent(Repository.class)) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.debug("Repository method - Method: {} | Return Value: {} | Execution Time: {} ms", methodName, result, executionTime);
        }

        return result;
    }
}
