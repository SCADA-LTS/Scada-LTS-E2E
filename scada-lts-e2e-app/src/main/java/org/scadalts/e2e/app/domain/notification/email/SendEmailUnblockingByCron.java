package org.scadalts.e2e.app.domain.notification.email;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;

@ConditionalOnProperty(prefix = "test.e2e.run-app", name = "test.e2e.run-app.unblock-send-email-by-cron:true")
public interface SendEmailUnblockingByCron {

    @Scheduled(cron = "${test.e2e.run-app.unblock-send-fail-email-cron:0 0 4/24 ? * * *}")
    @CacheEvict(cacheNames = SentEmailsCacheConfig.SENT_EMAILS)
    default void unblockSendFailEmail() {}

    @Scheduled(cron = "${test.e2e.run-app.unblock-send-success-email-cron:0 0 4/24 ? * * *}")
    @CacheEvict(cacheNames = SentEmailsCacheConfig.SENT_EMAILS_SUCCESS)
    default void unblockSendSuccessEmail() {}
}
