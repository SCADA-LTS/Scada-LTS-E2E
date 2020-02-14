package org.scadalts.e2e.app.infrastructure.config;

import lombok.Getter;

@Getter
public enum ConfigAppKeys {

    SERVER_PORT_KEY_X("--server.port={0}"),
    CRON_PATTERN_KEY_X("--test.e2e.run-app.cron-pattern={0}"),
    CONTINUOUS_MODE_KEY_X("--test.e2e.run-app.continuous-mode={0}"),
    NOTIFICATION_EMAIL_MODE_KEY_X("--test.e2e.run-app.notification-email-mode={0}"),
    DELETE_EMAIL_FROM_SENT_EMAILS_AFTER_MS_KEY_X("--test.e2e.run-app.delete-email-from-sent-emails-after-ms={0}");

    private String key;

    ConfigAppKeys(String key) {
        this.key = key;
    }
}
