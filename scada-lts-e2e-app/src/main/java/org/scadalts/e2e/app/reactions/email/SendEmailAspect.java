package org.scadalts.e2e.app.reactions.email;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Log4j2
class SendEmailAspect {

    @Pointcut("@within(org.scadalts.e2e.app.annotations.SendEmailReaction)")
    void sendEmail() {}

    @AfterReturning(value = "sendEmail()", returning = "returned")
    Object reaction(JoinPoint joinPoint, Object returned) throws Throwable {
        Object result = joinPoint.getTarget();
        logger.info("return: {}", returned);
        return result;
    }
}