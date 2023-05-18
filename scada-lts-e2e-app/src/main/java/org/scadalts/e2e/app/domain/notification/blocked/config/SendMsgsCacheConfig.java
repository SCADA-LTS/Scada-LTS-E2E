package org.scadalts.e2e.app.domain.notification.blocked.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class SendMsgsCacheConfig {

    @Value("${test.e2e.run-app.delete-email-from-sent-emails-after-ms:3600000}")
    private long expireMs;

    @Value("${test.e2e.run-app.unblock-send-email-by-cron:true}")
    private boolean unblockSendEmailByCron;

    public static final String SENT_MSG_FAIL = "sentMsgFail";
    public static final String SENT_MSG_BLOCKED = "sentMsgBlocked";
    public static final String MSG_CACHE_KEY = "T(java.util.Objects).hash(#p0?.failTestNames + #p0?.sendTo?.adress + #p0?.sendTo?.locale + #p0?.sendTo?.dest)";
    public static final String SENT_MSG_SUCCESS = "sentMsgSuccess";
    public static final String MSG_SUCCESS_CACHE_KEY = "T(java.util.Objects).hash(#p0?.sendTo?.adress + #p0?.sendTo?.locale + #p0?.sendTo?.dest)";

    @Bean
    public CacheManager cacheManager(Ticker ticker, E2eConfig e2eConfig) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(SENT_MSG_FAIL, SENT_MSG_SUCCESS, SENT_MSG_BLOCKED);
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

    @Bean
    public MsgCacheCleaner emailCacheCleaner() {
        return new MsgCacheCleaner() {};
    }

    @Bean
    @ConditionalOnProperty(prefix = "test.e2e.run-app", name = "unblock-send-email-by-cron")
    public SendMsgUnblockingByCron sendMsgUnblockingByCron() {
        return new SendMsgUnblockingByCron();
    }
}
