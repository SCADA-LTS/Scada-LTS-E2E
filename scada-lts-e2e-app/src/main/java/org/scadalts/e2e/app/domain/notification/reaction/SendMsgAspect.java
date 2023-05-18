package org.scadalts.e2e.app.domain.notification.reaction;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.scadalts.e2e.app.domain.notification.blocked.config.MsgCacheCleaner;
import org.scadalts.e2e.app.domain.notification.send.MsgData;
import org.scadalts.e2e.app.domain.notification.send.MsgService;
import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.common.core.config.SendTo;
import org.scadalts.e2e.test.core.plans.engine.E2eSummarable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Log4j2
@Logging
@Component
@ConditionalOnProperty(prefix = "test.e2e.run-app", name = "notification-email-mode")
public class SendMsgAspect {

    private final MsgCacheCleaner msgCacheCleaner;
    private final MsgService emailService;
    private final E2eConfig config;

    public SendMsgAspect(MsgCacheCleaner msgCacheCleaner, MsgService emailService, E2eConfig config) {
        this.msgCacheCleaner = msgCacheCleaner;
        this.emailService = emailService;
        this.config = config;
    }

    @Pointcut("@within(org.scadalts.e2e.app.domain.notification.reaction.SendMsgReaction)")
    void sendMsg() {
    }

    @AfterReturning(value = "sendMsg()", returning = "returned")
    public void reaction(Object returned) {
        if (returned instanceof E2eSummarable) {
            @SuppressWarnings("unchecked")
            E2eSummarable summary = (E2eSummarable) returned;
            if (!summary.wasSuccessful()) {
                for (SendTo sendTo : config.getSendTo()) {
                    MsgData msgData = MsgData.create(config, sendTo, summary);
                    MsgData fromCache = msgCacheCleaner.getMsgFail(msgData);
                    if(!isEquals(msgData, fromCache)) {
                        msgCacheCleaner.removeMsgFail(msgData);
                    }
                    emailService.sendFail(msgData);
                }
                msgCacheCleaner.removeMsgSuccessAll();
            } else {
                for (SendTo sendTo : config.getSendTo()) {
                    emailService.sendSuccess(MsgData.createSuccess(config, sendTo, summary));
                }
                msgCacheCleaner.removeMsgFailAll();
            }
        }
    }

    @AfterThrowing(value = "sendMsg()", throwing = "throwable")
    void reaction(JoinPoint joinPoint, Throwable throwable) {
        for (SendTo sendTo : config.getSendTo()) {
            MsgData msgData = MsgData.create(config, sendTo, _getMessageError(joinPoint, throwable), throwable);
            emailService.sendFail(msgData);
            msgCacheCleaner.removeMsgSuccessAll();
        }
    }

    private String _getMessageError(JoinPoint joinPoint, Throwable throwable) {
        String run = joinPoint.getSignature().getName();
        String error = MessageFormat.format("exception: {0}, message: {1}", throwable.getClass().getSimpleName(), throwable.getLocalizedMessage());
        return MessageFormat.format("\nrun: {0}\n\n error: {1}\n", run, error);
    }

    private static boolean isEquals(MsgData msgData, MsgData fromCache) {
        return msgData.getSummary().equals(fromCache.getSummary());
    }
}