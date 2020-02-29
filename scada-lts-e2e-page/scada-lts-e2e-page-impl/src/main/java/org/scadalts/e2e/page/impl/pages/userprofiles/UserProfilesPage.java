package org.scadalts.e2e.page.impl.pages.userprofiles;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.reloadElement;

public class UserProfilesPage extends MainPageObjectAbstract<UserProfilesPage> {

    public static final String TITLE = "Manage user profiles";

    public UserProfilesPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public UserProfilesPage getPage() {
        return this;
    }

    @Override
    public String getBodyText() {
        String bodyText = super.getBodyText();
        if(!bodyText.contains(getTitle())) {
            SelenideElement selenideElement = reloadElement($(".smallTitle"));
            return selenideElement.getText();
        }
        return bodyText;
    }
}