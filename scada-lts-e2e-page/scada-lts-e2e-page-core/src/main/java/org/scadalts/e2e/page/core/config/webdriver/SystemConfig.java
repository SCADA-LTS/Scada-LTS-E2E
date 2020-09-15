package org.scadalts.e2e.page.core.config.webdriver;

import lombok.Getter;
import org.openqa.selenium.Keys;

@Getter
public enum SystemConfig implements GetConfigForSystem {

    WINDOWS_32("win", Keys.CONTROL),
    MAC_64("mac", Keys.COMMAND),
    LINUX_64("nux", Keys.CONTROL);

    private final String systemKey;
    private final Keys ctrl;

    SystemConfig(String systemKey, Keys ctrl) {
        this.systemKey = systemKey;
        this.ctrl = ctrl;
    }

    public static SystemConfig getConfig() {
        return GetConfigForSystem.getConfig(SystemConfig.values(),
                SystemConfig.WINDOWS_32);
    }
}
