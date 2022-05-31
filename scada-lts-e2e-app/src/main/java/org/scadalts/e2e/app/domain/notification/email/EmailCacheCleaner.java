package org.scadalts.e2e.app.domain.notification.email;

import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface EmailCacheCleaner {

    @Logging
    @CacheEvict(cacheNames = SentEmailsCacheConfig.SENT_EMAILS_SUCCESS, allEntries = true)
    default void removeEmailSuccessAll() {}

    @Logging
    @CacheEvict(cacheNames = {SentEmailsCacheConfig.SENT_EMAILS, SentEmailsCacheConfig.SENT_EMAILS_BLOCKED}, allEntries = true)
    default void removeEmailFailAll() {}

    @Logging
    @CacheEvict(cacheNames = {SentEmailsCacheConfig.SENT_EMAILS, SentEmailsCacheConfig.SENT_EMAILS_BLOCKED}, key = SentEmailsCacheConfig.EMAIL_CACHE_KEY)
    default void removeEmailFail(EmailData emailData) {}

    @Logging
    @Cacheable(cacheNames = SentEmailsCacheConfig.SENT_EMAILS_BLOCKED, key = SentEmailsCacheConfig.EMAIL_CACHE_KEY)
    default EmailData getEmailFail(EmailData emailData) {
        return emailData;
    }
}
