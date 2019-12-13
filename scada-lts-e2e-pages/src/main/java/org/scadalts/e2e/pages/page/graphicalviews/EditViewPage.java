package org.scadalts.e2e.pages.page.graphicalviews;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.pages.component.E2eUtils;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class EditViewPage {

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

    public void setViewName(String viewName) {
        this.viewName.clear();
        this.viewName.sendKeys(viewName);
    }

    public void chooseFile(File file) {
        chooseFile.uploadFile(file);
    }

    public void uploadFile() {
        uploadFile.click();
    }

    public void save() {
        save.click();
    }

    public String selectComponentByName(String componentName) {
        componentList.selectOption(componentName);
        return componentList.getValue();
    }

    public void addViewComponent() {
        addViewComponent.click();
    }

    public void dragAndDropViewComponent() {
        Action action = new Actions(getWebDriver())
                .dragAndDrop($(By.id("c1Content")), $(By.id("viewContent")))
                .build();
        action.perform();
    }

    public void delete() {
        deleteCheckbox.click();
        deleteButton.click();
        E2eUtils.acceptAlert();
    }
}
