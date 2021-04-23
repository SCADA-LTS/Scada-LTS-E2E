package org.scadalts.e2e.page.impl.pages.users;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

public class UsersPage extends MainPageObjectAbstract<UsersPage> {

    @FindBy(css = "a[href='users.shtm']")
    private SelenideElement source;

    @FindBy(id = "userList")
    private SelenideElement userList;

    public static final String TITLE = "Users";

    public UsersPage() {
        super(TITLE, "/users.shtm");
    }

    @Override
    public UsersPage getPage() {
        return this;
    }

    @Override
    public String getBodyText() {
        String bodyText = super.getBodyText();
        if(!bodyText.contains(getTitle())) {
            SelenideElement selenideElement = waitWhileNotVisible($(".smallTitle"));
            return selenideElement.getText();
        }
        return bodyText;
    }

    @Override
    public UsersPage waitForCompleteLoad() {
        waitWhile(userList, not(Condition.visible));
        return super.waitForCompleteLoad();
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}