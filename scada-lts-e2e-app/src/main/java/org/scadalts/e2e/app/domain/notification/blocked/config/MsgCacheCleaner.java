package org.scadalts.e2e.app.domain.notification.blocked.config;

import org.scadalts.e2e.app.domain.notification.send.MsgData;
import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface MsgCacheCleaner {

    @Logging
    @CacheEvict(cacheNames = SendMsgsCacheConfig.SENT_MSG_SUCCESS, allEntries = true)
    default void removeMsgSuccessAll() {}

    @Logging
    @CacheEvict(cacheNames = {SendMsgsCacheConfig.SENT_MSG_FAIL, SendMsgsCacheConfig.SENT_MSG_BLOCKED}, allEntries = true)
    default void removeMsgFailAll() {}

    @Logging
    @CacheEvict(cacheNames = {SendMsgsCacheConfig.SENT_MSG_FAIL, SendMsgsCacheConfig.SENT_MSG_BLOCKED}, key = SendMsgsCacheConfig.MSG_CACHE_KEY)
    default void removeMsgFail(MsgData msgData) {}

    @Logging
    @Cacheable(cacheNames = SendMsgsCacheConfig.SENT_MSG_BLOCKED, key = SendMsgsCacheConfig.MSG_CACHE_KEY)
    default MsgData getMsgFail(MsgData msgData) {
        return msgData;
    }
}
