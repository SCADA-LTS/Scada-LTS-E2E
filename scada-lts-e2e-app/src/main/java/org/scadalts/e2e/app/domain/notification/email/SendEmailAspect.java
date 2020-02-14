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
import java.util.Set;

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

                Set<String> failTestNames = summary.getFailTestNames();
                logger.debug("failTestNames: {}", failTestNames);
                EmailData emailData = EmailData.builder()
                        .content("")
                        .title(config.getTitleEmail())
                        .header("Scada-LTS-E2E")
                        .from(config.getSendFrom())
                        .to(config.getSendTo())
                        .failTestNames(failTestNames)
                        .summary(summary)
                        .build();

                emailService.sendEmail(emailData);
            }
        }
    }

    @AfterThrowing(value = "sendEmail()", throwing = "throwable")
    void reaction(JoinPoint joinPoint, Throwable throwable) {
        EmailData emailData = EmailData.builder()
                .content(_getMessageError(joinPoint, throwable))
                .title(config.getTitleEmail())
                .header("Scada-LTS-E2E")
                .summary(E2eResultSummary.empty())
                .from(config.getSendFrom())
                .to(config.getSendTo())
                .failTestName(throwable.getClass().getName())
                .build();

        emailService.sendEmail(emailData);
    }

    private String _getMessageError(JoinPoint joinPoint, Throwable throwable) {
        String run = joinPoint.getSignature().getName();
        String error = MessageFormat.format("exception: {0}, message: {1}", throwable.getClass().getSimpleName(), throwable.getLocalizedMessage());
        return MessageFormat.format("\nrun: {0}\n\n error: {1}\n", run, error);
    }
}