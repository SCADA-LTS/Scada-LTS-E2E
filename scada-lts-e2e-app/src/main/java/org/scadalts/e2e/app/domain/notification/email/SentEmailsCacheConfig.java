package org.scadalts.e2e.app.domain.notification.email;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
class SentEmailsCacheConfig {

    @Value("${test.e2e.run-app.delete-email-from-sent-emails-after-ms:3600000}")
    private long expireMs;

    @Value("${test.e2e.run-app.unblock-send-email-by-cron:true}")
    private boolean unblockSendEmailByCron;

    static final String SENT_EMAILS = "sentEmails";
    static final String EMAIL_CACHE_KEY = "T(java.util.Objects).hash(#emailData?.failTestNames + #emailData?.sendTo?.adress + #emailData?.sendTo?.locale)";
    static final String SENT_EMAILS_SUCCESS = "sentEmailsSuccess";
    static final String EMAIL_SUCCESS_CACHE_KEY = "T(java.util.Objects).hash(#emailData?.sendTo?.adress + #emailData?.sendTo?.locale)";

    @Bean
    public CacheManager cacheManager(Ticker ticker, E2eConfig e2eConfig) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(SENT_EMAILS, SENT_EMAILS_SUCCESS);
        if(unblockSendEmailByCron) {
            cacheManager.setCaffeine(Caffeine.newBuilder()
                    .maximumSize(e2eConfig.getSendTo().size())
                    .ticker(ticker));
        } else {
            cacheManager.setCaffeine(Caffeine.newBuilder()
                    .expireAfterWrite(expireMs, TimeUnit.MILLISECONDS)
                    .maximumSize(e2eConfig.getSendTo().size())
                    .ticker(ticker));
        }
        return cacheManager;
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }
}
