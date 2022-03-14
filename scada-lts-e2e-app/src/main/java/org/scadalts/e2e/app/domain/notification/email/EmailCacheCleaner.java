package org.scadalts.e2e.app.domain.notification.email;

import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.springframework.cache.annotation.CacheEvict;

public interface EmailCacheCleaner {

    @Logging
    @CacheEvict(cacheNames = SentEmailsCacheConfig.SENT_EMAILS_SUCCESS, allEntries = true)
    default void removeEmailSuccessAll() {}

    @Logging
    @CacheEvict(cacheNames = SentEmailsCacheConfig.SENT_EMAILS, allEntries = true)
    default void removeEmailFailAll() {}
}
