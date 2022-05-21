package org.scadalts.e2e.app.infrastructure.config;

import lombok.Getter;

@Getter
public enum ConfigAppKeys {

    SERVER_PORT_KEY_X("--server.port={0}"),
    CRON_PATTERN_KEY_X("--test.e2e.run-app.cron-pattern={0}"),
    CONTINUOUS_MODE_KEY_X("--test.e2e.run-app.continuous-mode={0}"),
    NOTIFICATION_EMAIL_MODE_KEY_X("--test.e2e.run-app.notification-email-mode={0}"),
    DELETE_EMAIL_FROM_SENT_EMAILS_AFTER_MS_KEY_X("--test.e2e.run-app.delete-email-from-sent-emails-after-ms={0}"),
    REFRESH_SESSION_CRON_KEY_X("--test.e2e.run-app.refresh-session-cron={0}"),
    UNBLOCK_SEND_SUCCESS_EMAIL_CROM("--test.e2e.run-app.unblock-send-success-email-cron={0}"),
    UNBLOCK_SEND_FAIL_EMAIL_CROM("--test.e2e.run-app.unblock-send-fail-email-cron={0}"),
    UNBLOCK_SEND_EMAIL_BY_CROM("--test.e2e.run-app.unblock-send-email-by-cron={0}");

    private String key;

    ConfigAppKeys(String key) {
        this.key = key;
    }
}
