package org.scadalts.e2e.page.impl.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.common.core.exceptions.ApplicationIsNotAvailableException;
import org.scadalts.e2e.common.core.exceptions.ApplicationTooHighLoadException;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage extends PageObjectAbstract<LoginPage> {

    @FindBy(id = "username")
    private SelenideElement userName;

    @FindBy(id = "password")
    private SelenideElement password;

    @FindBy(css = ".login-button input[type='submit']")
    private SelenideElement loginButton;

    private static final String URL_REF = "/login.htm";

    public static final String TITLE = "Login";

    public LoginPage() {
        super();
    }

    public static LoginPage openPage() {
        try {
            return open(URL_REF, LoginPage.class);
        } catch (SessionNotCreatedException th) {
            throw new SessionNotCreatedException("Test tool problem - " + th.getMessage());
        }  catch (TimeoutException th) {
            throw new ApplicationTooHighLoadException(E2eConfiguration.baseUrl + URL_REF, Configuration.pageLoadTimeout);
        } catch (Throwable th) {
            if((th.getCause() instanceof ConnectException) ||
                    (th.getCause() instanceof SocketTimeoutException)) {
                throw new ApplicationIsNotAvailableException("");
            }
            throw new ApplicationIsNotAvailableException(th);
        }
    }

    @Override
    public LoginPage getPage() {
        return this;
    }

    public LoginPage setUserName(String userName) {
        this.userName.clear();
        this.userName.setValue(userName);
        return this;
    }

    public LoginPage setPassword(String password) {
        this.password.clear();
        this.password.setValue(password);
        return this;
    }

    public NavigationPage login() {
        this.loginButton.click();
        printCurrentUrl();
        return NavigationPage.openPage(getCurrentUrl());
    }

    public static String getUrlRef() {
        return URL_REF;
    }
}
