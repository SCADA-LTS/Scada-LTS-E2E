package org.scadalts.e2e.pages.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.config.E2eConfig;
import org.scadalts.e2e.config.E2eConfigKeys;
import org.scadalts.e2e.pages.page.main.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage implements Maximizable {

    @FindBy(tagName = "body")
    private SelenideElement body;

    @FindBy(id = "username")
    private SelenideElement userName;

    @FindBy(id = "password")
    private SelenideElement password;

    @FindBy(css = "input[value='Login']")
    private SelenideElement loginButton;

    private static final String URL_REF = "/login.htm";

    private final E2eConfig config = E2eConfig.INSTANCE;

    public static LoginPage openPage() {
        return open(URL_REF, LoginPage.class);
    }

    public void setUserName() {
        this.userName.clear();
        this.userName.sendKeys(config.getString(E2eConfigKeys.USER, "Admin"));
    }

    public void setUserName(String userName) {
        this.userName.clear();
        this.userName.sendKeys(userName);
    }

    public void setPassword() {
        this.password.clear();
        this.password.sendKeys(config.getString(E2eConfigKeys.PASSWORD, "Admin"));
    }

    public void setPassword(String password) {
        this.password.clear();
        this.password.sendKeys(password);
    }

    public MainPage login() {
        this.loginButton.click();
        return page(MainPage.openPage());
    }
}
