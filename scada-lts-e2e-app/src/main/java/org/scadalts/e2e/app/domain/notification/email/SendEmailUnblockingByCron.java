package org.scadalts.e2e.app.domain.notification.email;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "test.e2e.run-app", name = "unblock-send-email-by-cron")
public class SendEmailUnblockingByCron {

    @Scheduled(cron = "${test.e2e.run-app.unblock-send-fail-email-cron:0 0 4/24 ? * * *}")
    @CacheEvict(cacheNames = SentEmailsCacheConfig.SENT_EMAILS, allEntries = true)
    public void unblockSendFailEmail() {}

    @Scheduled(cron = "${test.e2e.run-app.unblock-send-success-email-cron:0 0 4/24 ? * * *}")
    @CacheEvict(cacheNames = SentEmailsCacheConfig.SENT_EMAILS_SUCCESS, allEntries = true)
    public void unblockSendSuccessEmail() {}
}
