package org.scadalts.e2e.page.impl.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage extends PageObjectAbstract<LoginPage> {

    @FindBy(id = "username")
    private SelenideElement userName;

    @FindBy(id = "password")
    private SelenideElement password;

    @FindBy(css = "input[value='Login']")
    private SelenideElement loginButton;

    private static final String URL_REF = "/login.htm";

    public static final String TITLE = "Login";

    public LoginPage() {
        super(TITLE);
    }

    public static LoginPage openPage() {
        return open(URL_REF, LoginPage.class);
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
        return page(NavigationPage.openPage());
    }

    public static String getUrlRef() {
        return URL_REF;
    }
}
