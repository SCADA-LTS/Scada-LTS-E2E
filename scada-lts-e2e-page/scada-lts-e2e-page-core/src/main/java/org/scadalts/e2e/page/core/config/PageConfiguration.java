package org.scadalts.e2e.page.core.config;

import org.openqa.selenium.Keys;
import org.scadalts.e2e.page.core.config.webdriver.GetConfigForSystem;
import org.scadalts.e2e.page.core.config.webdriver.SystemConfig;

import java.io.File;

public class PageConfiguration {

    public volatile static File driverFile = null;
    public volatile static Keys ctrl = SystemConfig.getConfig().getCtrl();
    public volatile static int clickDelayMs = 500;
    public volatile static long timeout = 6001;

}
