package org.scadalts.e2e.page.impl.pages.users;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.reloadElement;

public class UsersPage extends MainPageObjectAbstract<UsersPage> {

    public static final String TITLE = "Users";

    public UsersPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public UsersPage getPage() {
        return this;
    }

    @Override
    public String getTitle() {
        return TITLE;
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