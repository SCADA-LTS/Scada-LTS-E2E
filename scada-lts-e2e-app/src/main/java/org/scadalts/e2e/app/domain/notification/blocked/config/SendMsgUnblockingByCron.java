package org.scadalts.e2e.app.domain.notification.blocked.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

public class SendMsgUnblockingByCron {

    @Scheduled(cron = "${test.e2e.run-app.unblock-send-fail-email-cron:0 5 4 * * ?}")
    @CacheEvict(cacheNames = SendMsgsCacheConfig.SENT_MSG_FAIL, allEntries = true)
    public void unblockSendFailMsg() {
    }

    @Scheduled(cron = "${test.e2e.run-app.unblock-send-success-email-cron:0 5 4 * * ?}")
    @CacheEvict(cacheNames = SendMsgsCacheConfig.SENT_MSG_SUCCESS, allEntries = true)
    public void unblockSendSuccessMsg() {
    }
}
