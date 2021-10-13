package org.scadalts.e2e.page.impl.pages.users;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.identifiers.UserIdentifier;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.criterias.Tag.td;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;
import static org.scadalts.e2e.page.core.xpaths.XpathExpression.xpath;
import static org.scadalts.e2e.page.core.xpaths.XpathOperation.equal;

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

    public EditUsersPage openUserEditor(UserIdentifier identifier) {
        delay();
        SelenideElement element = findObject(identifier.getNodeCriteria(), userList);
        element.click();
        printCurrentUrl();
        return page(new EditUsersPage(element));
    }

    public List<UserIdentifier> getUsers() {
        List<WebElement> elements = userList.findElements(By.xpath(xpath(td(), equal(clazz("link"))).expression()));
        return elements.stream().filter(a -> !a.getText().isEmpty())
                .map(a -> new UserIdentifier(a.getText()))
                .collect(Collectors.toList());
    }

    public List<E2eWebElement> getUsersClick() {
        List<WebElement> elements = userList.findElements(By.xpath(xpath(td(), equal(clazz("link"))).expression()));
        return elements.stream().filter(a -> !a.getText().isEmpty())
                .map(a -> E2eWebElement.newInstance($(a)))
                .collect(Collectors.toList());
    }
}