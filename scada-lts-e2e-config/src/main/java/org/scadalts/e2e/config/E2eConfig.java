package org.scadalts.e2e.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public enum E2eConfig {

    INSTANCE;

    private final Properties config;
    private static Logger logger = LoggerFactory.getLogger(E2eConfig.class);

    E2eConfig() {
        this.config = _init();
    }

    public int getInt(E2eConfigKey configKey, int defaultValue) {
        String key = configKey.key();
        String value = String.valueOf(defaultValue);
        String raw = config.getProperty(key, value);
        return Integer.parseInt(raw);
    }

    public long getLong(E2eConfigKey configKey, long defaultValue) {
        String key = configKey.key();
        String value = String.valueOf(defaultValue);
        String raw = config.getProperty(key, value);
        return Long.parseLong(raw);
    }

    public String getString(E2eConfigKey configKey, String defaultValue) {
        String key = configKey.key();
        return config.getProperty(key, defaultValue);
    }

    public boolean getBoolean(E2eConfigKey configKey, boolean defaultValue) {
        String key = configKey.key();
        String value = String.valueOf(defaultValue);
        String raw = config.getProperty(key, value);
        return Boolean.parseBoolean(raw);
    }

    public E2eConfig updateConfig(Properties console) {
        config.putAll(console);
        return this;
    }

    @Override
    public String toString() {
        return "E2eConfig{" +
                "config=" + config +
                '}';
    }

    private static Properties _init() {
        try {
            Properties properties = new Properties();
            properties.load(FileUtils.getResourceAsStream("e2e/config/scadalts-e2e-config.properties"));
            return properties;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return new Properties();
        }
    }
}
