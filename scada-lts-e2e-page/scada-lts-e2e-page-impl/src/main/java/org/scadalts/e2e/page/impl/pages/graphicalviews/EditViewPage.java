package org.scadalts.e2e.page.impl.pages.graphicalviews;

import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.scadalts.e2e.page.core.util.E2eUtil.acceptAlert;
import static org.scadalts.e2e.page.core.util.StabilityUtil.reloadElement;

public class EditViewPage extends PageObjectAbstract<EditViewPage> {

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

    public EditViewPage() {
        super(TITLE);
    }

    @Override
    public EditViewPage getPage() {
        return this;
    }

    public EditViewPage setViewName(String viewName) {
        this.viewName.clear();
        this.viewName.sendKeys(viewName);
        return this;
    }

    public EditViewPage chooseFile(File file) {
        chooseFile.uploadFile(file);
        return this;
    }

    public EditViewPage uploadFile() {
        uploadFile.click();
        return this;
    }

    public EditViewPage save() {
        save.click();
        return this;
    }

    public EditViewPage selectComponentByName(String componentName) {
        componentList.selectOption(componentName);
        return this;
    }

    public EditViewPage addViewComponent() {
        addViewComponent.click();
        return this;
    }

    public EditViewPage dragAndDropViewComponent() {
        Action action = new Actions(getWebDriver())
                .dragAndDrop($(By.id("c1Content")), $(By.id("viewContent")))
                .build();
        action.perform();
        return this;
    }

    public void delete() {
        deleteCheckbox.click();
        deleteButton.click();
        acceptAlert();
    }

    public String getElementDynamic(String css) {
        return $(By.cssSelector(css)).innerHtml();
    }

    public String getFirstAlarmListText() {
        return _getObject("#c1Content");
    }

    public String getSecondAlarmListText() {
        return _getObject("#c2Content");
    }

    private String _getObject(String css) {
        SelenideElement table = $(By.cssSelector(css));
        String object = table.text();
        return StringUtils.isBlank(object) ? reloadElement(table).getText() : object;
    }
}
