package org.scadalts.e2e.page.core.config.webdriver;

import java.util.stream.Stream;

public interface WebDrvierPathConfig {
    String OS_NAME_KEY = "os.name";
    String getSystemKey();
    String getPath();

    static WebDrvierPathConfig getDriverPathConfig(WebDrvierPathConfig[] configs, WebDrvierPathConfig valueDefault) {
        String osName = System.getProperty(OS_NAME_KEY, "win");
        return Stream.of(configs)
                .filter(a -> osName.toLowerCase().contains(a.getSystemKey()))
                .findFirst()
                .orElse(valueDefault);
    }
}
