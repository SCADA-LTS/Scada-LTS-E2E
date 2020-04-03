package org.scadalts.e2e.common.config;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class ConfigHandler {

    private static E2eConfig CONFIG = new E2eConfigFromFileProvider().get();
    private static final Object lock = new Object();

    private ConfigHandler() {
    }

    public static E2eConfig handle(E2eConfig config) {
        if (!CONFIG.equals(config)) {
            logger.info("Config handle...");
            synchronized (lock) {
                CONFIG = config;
            }
        }
        return getConfig();
    }

    public static E2eConfig getConfig() {
        synchronized (lock) {
            return CONFIG;
        }
    }
}
