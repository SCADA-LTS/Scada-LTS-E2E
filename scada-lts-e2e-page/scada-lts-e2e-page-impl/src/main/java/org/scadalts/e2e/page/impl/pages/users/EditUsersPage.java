package org.scadalts.e2e.page.impl.pages.users;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.identifiers.UserIdentifier;

import static com.codeborne.selenide.Condition.not;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

public class EditUsersPage extends PageObjectAbstract<EditUsersPage> {

    @FindBy(className = "borderDiv")
    private SelenideElement userDetails;

    @FindBy(id = "username")
    private SelenideElement username;

    private SelenideElement source;

    public static final String TITLE = "User details";

    public EditUsersPage(SelenideElement source) {
        this.source = source;
    }

    public EditUsersPage setName(UserIdentifier identifier) {
        delay();
        waitWhile(username, not(Condition.visible)).setValue(identifier.getValue());
        return this;
    }

    @Override
    public EditUsersPage getPage() {
        return this;
    }

    @Override
    public EditUsersPage waitForObject(IdentifierObject identifier) {
        delay();
        waitWhile(b -> !identifier.getValue().equals(b.getValue()),
                waitWhile(username, not(Condition.visible)),
                true);
        return this;
    }
}
