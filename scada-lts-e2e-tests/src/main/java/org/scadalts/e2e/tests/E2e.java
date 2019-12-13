package org.scadalts.e2e.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.scadalts.e2e.pages.config.E2eConfigurator;
import org.scadalts.e2e.pages.page.LoginPage;
import org.scadalts.e2e.pages.page.main.MainPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.scadalts.e2e.config.FileUtils.toFile;

public abstract class E2e {

    private static MainPage mainPage;
    private static Logger logger = LoggerFactory.getLogger(E2e.class);

    @BeforeClass
    public static void setup() {
        E2eConfigurator.init();
        LoginPage loginPage = LoginPage.openPage();
        loginPage.maximize();
        loginPage.setUserName();
        loginPage.setPassword();
        mainPage = loginPage.login();
    }

    @AfterClass
    public static void logout() {
        mainPage.logout();
        mainPage.closeWindows();
    }

    public static MainPage getMainPage() {
        return mainPage;
    }

    public static File getBackgroundFile() {
        try {
            return toFile("background-test.png");
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(),throwable);
            return null;
        }
    }

}
