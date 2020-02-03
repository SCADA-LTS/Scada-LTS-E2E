package org.scadalts.e2e.common.config;

public class E2eConfigurator {

    public static void init(E2eConfig config) {
        E2eConfiguration.authType = config.getAuthType();
        E2eConfiguration.baseUrl = config.getBaseUrl();
        E2eConfiguration.password = config.getPassword();
        E2eConfiguration.userName = config.getUserName();
    }
}
