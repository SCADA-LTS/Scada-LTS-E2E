package org.scadalts.e2e.config;

public enum E2eConfigKeys implements E2eConfigKey {

    URL("e2eUrl"),
    HEADLESS("e2eHeadless"),
    BROWSER("e2eBrowser"),
    TIMEOUT("e2eTimeout"),
    ENVIRONMENT("e2eEnv"),
    USER("e2eUser"),
    PASSWORD("e2ePassword"),
    CTRL("e2eCtrl"),
    CONFIG("e2eConfig");

    private String key;

    E2eConfigKeys(String key) {
        this.key = key;
    }

    @Override
    public String key() {
        return key;
    }
}
