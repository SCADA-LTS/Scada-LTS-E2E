package org.scadalts.e2e.app.domain.notification.email;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
class SentEmailsCacheConfig {

    @Value("${test.e2e.run-app.delete-email-from-sent-emails-after-ms:3600000}")
    private long expireMs;

    static final String SENT_EMAILS = "sentEmails";
    static final String EMAIL_CACHE_KEY = "T(java.util.Objects).hash(#emailData?.failTestNames)";

    @Bean
    public CacheManager cacheManager(Ticker ticker) {
        CaffeineCache messageCache = new CaffeineCache(SENT_EMAILS, Caffeine.newBuilder()
                .expireAfterWrite(expireMs, TimeUnit.MILLISECONDS)
                .maximumSize(100)
                .ticker(ticker)
                .build());
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(messageCache));
        return manager;
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }

}
