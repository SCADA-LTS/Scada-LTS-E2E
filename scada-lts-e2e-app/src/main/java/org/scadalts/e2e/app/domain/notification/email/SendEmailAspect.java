package org.scadalts.e2e.app.domain.notification.email;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.core.plan.runner.E2eResultSummary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Log4j2
@Logging
@Component
@ConditionalOnProperty(prefix = "test.e2e.run-app", name = "notification-email-mode")
class SendEmailAspect {

    private final EmailService emailService;
    private final E2eConfig config;

    public SendEmailAspect(EmailService emailService, E2eConfig config) {
        this.emailService = emailService;
        this.config = config;
    }

    @Pointcut("@within(org.scadalts.e2e.app.domain.notification.email.SendEmailReaction)")
    void sendEmail() {
    }

    @AfterReturning(value = "sendEmail()", returning = "returned")
    void reaction(Object returned) {
        if (returned instanceof E2eResultSummary) {
            @SuppressWarnings("unchecked")
            E2eResultSummary summary = (E2eResultSummary) returned;
            if (!summary.wasSuccessful()) {
                EmailData emailData = EmailData.create(config, summary);
                emailService.sendEmail(emailData);
            }
        }
    }

    @AfterThrowing(value = "sendEmail()", throwing = "throwable")
    void reaction(JoinPoint joinPoint, Throwable throwable) {
        EmailData emailData = EmailData.create(config, _getMessageError(joinPoint, throwable), throwable);
        emailService.sendEmail(emailData);
    }

    private String _getMessageError(JoinPoint joinPoint, Throwable throwable) {
        String run = joinPoint.getSignature().getName();
        String error = MessageFormat.format("exception: {0}, message: {1}", throwable.getClass().getSimpleName(), throwable.getLocalizedMessage());
        return MessageFormat.format("\nrun: {0}\n\n error: {1}\n", run, error);
    }


}