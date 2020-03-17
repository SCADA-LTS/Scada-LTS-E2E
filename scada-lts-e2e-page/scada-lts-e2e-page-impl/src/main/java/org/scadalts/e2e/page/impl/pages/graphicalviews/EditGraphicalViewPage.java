package org.scadalts.e2e.page.impl.pages.graphicalviews;

import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

public class EditGraphicalViewPage extends PageObjectAbstract<EditGraphicalViewPage> {

    @FindBy(css = "input[name='view.name']")
    private SelenideElement viewName;

    @FindBy(css = "input[name='backgroundImageMP']")
    private SelenideElement chooseFile;

    @FindBy(css = "input[name='upload']")
    private SelenideElement uploadFile;

    @FindBy(css = "input[name='save']")
    private SelenideElement save;

    @FindBy(id = "deleteCheckbox")
    private SelenideElement deleteCheckbox;

    @FindBy(id = "deleteButton")
    private SelenideElement deleteButton;

    @FindBy(id = "componentList")
    private SelenideElement componentList;

    @FindBy(css = "img[onclick='addViewComponent()']")
    private SelenideElement addViewComponent;

    public static final String TITLE = "";

    private GraphicalViewsPage parent;

    public EditGraphicalViewPage(GraphicalViewsPage parent) {
        super(TITLE);
        this.parent = parent;
    }

    @Override
    public EditGraphicalViewPage getPage() {
        return this;
    }

    public EditGraphicalViewPage setViewName(GraphicalViewIdentifier viewName) {
        delay();
        this.viewName.clear();
        this.viewName.setValue(viewName.getValue());
        return this;
    }

    public EditGraphicalViewPage chooseFile(File file) {
        delay();
        chooseFile.uploadFile(file);
        return this;
    }

    public EditGraphicalViewPage uploadFile() {
        delay();
        uploadFile.click();
        return this;
    }

    public GraphicalViewsPage save() {
        delay();
        save.click();
        return parent;
    }

    public EditGraphicalViewPage selectComponentByName(String componentName) {
        delay();
        componentList.selectOption(componentName);
        return this;
    }

    public EditGraphicalViewPage addViewComponent() {
        delay();
        addViewComponent.click();
        return this;
    }

    public EditGraphicalViewPage dragAndDropViewComponent() {
        delay();
        Action action = new Actions(getWebDriver())
                .dragAndDrop($(By.id("c1Content")), $(By.id("viewBackground")))
                .build();
        action.perform();
        return this;
    }

    public EditGraphicalViewPage clickCheckboxDelete() {
        delay();
        deleteCheckbox.click();
        return this;
    }


    public GraphicalViewsPage delete() {
        delay();
        deleteCheckbox.click();
        acceptAlertAfterClick(deleteButton);
        return parent;
    }

    public String getElementDynamic(String css) {
        delay();
        return $(By.cssSelector(css)).innerHtml();
    }

    public String getFirstAlarmListText() {
        return _getObject("#c1Content");
    }

    public String getSecondAlarmListText() {
        return _getObject("#c2Content");
    }

    private String _getObject(String css) {
        delay();
        SelenideElement table = $(By.cssSelector(css));
        String object = table.text();
        return StringUtils.isBlank(object) ? waitWhileNotVisible(table).getText() : object;
    }
}
