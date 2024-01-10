package org.scadalts.e2e.page.core.config;

import org.openqa.selenium.Keys;
import org.scadalts.e2e.page.core.config.webdriver.SystemConfig;

import java.io.File;

public class PageConfiguration {

    public static File driverFile = null;
    public static Keys ctrl = SystemConfig.getConfig().getCtrl();
    public static int clickDelayMs = 500;
    public static long timeout = 6001;
    public static boolean headless = true;
    public static String reportsUrl = "";
    public static String baseUrl = "http://localhost:8080/";

}
