package org.scadalts.e2e.page.impl.pages.userprofiles;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

public class UserProfilesPage extends MainPageObjectAbstract<UserProfilesPage> {

    @FindBy(css = "a[href='usersProfiles.shtm']")
    private SelenideElement source;

    @FindBy(id ="userProfileList")
    private SelenideElement userProfileList;

    public static final String TITLE = "Manage user profiles";

    public UserProfilesPage() {
        super(TITLE, "/usersProfiles.shtm");
    }

    @Override
    public UserProfilesPage getPage() {
        return this;
    }

    @Override
    public String getBodyText() {
        String bodyText = super.getBodyText();
        if(!bodyText.contains(TITLE)) {
            SelenideElement selenideElement = waitWhileNotVisible($(".smallTitle"));
            return selenideElement.getText();
        }
        return bodyText;
    }

    @Override
    public UserProfilesPage waitForCompleteLoad() {
        waitWhile(userProfileList, not(Condition.visible));
        return super.waitForCompleteLoad();
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}