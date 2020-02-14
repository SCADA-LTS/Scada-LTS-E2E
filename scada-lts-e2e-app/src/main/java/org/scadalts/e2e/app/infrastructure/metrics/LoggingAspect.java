package org.scadalts.e2e.app.infrastructure.metrics;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class LoggingAspect {

    @Pointcut("@within(org.scadalts.e2e.app.infrastructure.metrics.Logging)")
    void logging() {}

    @Before(value = "logging()")
    void reactionBefore(JoinPoint joinPoint) throws Throwable {
        logger.debug("run: {}, args: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "logging()", returning = "returned")
    void reactionAfter(JoinPoint joinPoint, Object returned) throws Throwable {
        logger.debug("runned: {}, return: {}", joinPoint.getSignature().getName(), returned);
    }
}
