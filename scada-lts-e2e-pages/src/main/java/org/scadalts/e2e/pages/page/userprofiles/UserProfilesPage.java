package org.scadalts.e2e.pages.page.userprofiles;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

import static com.codeborne.selenide.Selenide.$;

public class UserProfilesPage extends PageObjectAbstract<UserProfilesPage> {

    public UserProfilesPage(SelenideElement source) {
        super(source);
    }

    @Override
    public UserProfilesPage getPage() {
        return this;
    }

    @Override
    public String getBodyText() {
        $(".smallTitle").click();
        return $(".smallTitle").getText();
    }
}