package org.scadalts.e2e.page.core.config.webdriver;

import java.util.stream.Stream;

public interface GetConfigForSystem {

    String OS_NAME_KEY = "os.name";

    String getSystemKey();

    static <T extends GetConfigForSystem> T getConfig(T[] configs, T valueDefault) {
        String osName = System.getProperty(OS_NAME_KEY, "win");
        return Stream.of(configs)
                .filter(a -> osName.toLowerCase().contains(a.getSystemKey()))
                .findFirst()
                .orElse(valueDefault);
    }
}
