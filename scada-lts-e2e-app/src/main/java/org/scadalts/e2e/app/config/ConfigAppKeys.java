package org.scadalts.e2e.app.config;

import lombok.Getter;

@Getter
public enum ConfigAppKeys {

    SERVER_PORT_KEY_X("--server.port={0}"),
    CRON_PATTERN_KEY_X("--test.e2e.run-app.cron-pattern={0}"),
    SCHEDULING_ENABLED_KEY_X("--test.e2e.run-app.scheduling-mode={0}");

    private String key;

    ConfigAppKeys(String key) {
        this.key = key;
    }
}
