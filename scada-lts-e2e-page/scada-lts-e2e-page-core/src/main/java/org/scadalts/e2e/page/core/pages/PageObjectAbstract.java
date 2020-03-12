package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.utils.MeasurePrinter;

import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

@Log4j2
public abstract class PageObjectAbstract<T extends PageObject<T>> implements PageObject<T> {

    @FindBy(tagName = "head")
    private SelenideElement head;

    @FindBy(tagName = "body")
    private SelenideElement body;

    private final String title;

    public PageObjectAbstract(String title) {
        this.title = title;
    }

    @Override
    public String getHeadHtml() {
        return head.innerHtml();
    }

    @Override
    public String getBodyText() {
        String bodyText = body.getText();
        if(StringUtils.isNotBlank(bodyText) && bodyText.contains(title))
            return bodyText;
        SelenideElement element = waitWhile(body, Condition.exactTextCaseSensitive(title));
        return element.getText();
    }

    @Override
    public String getBodyHtml() {
        String innerHtml = body.innerHtml();
        if(innerHtml.contains(title))
            return innerHtml;
        SelenideElement element = waitWhile(body, Condition.exactTextCaseSensitive(title));
        return element.innerHtml();
    }

    @Override
    public T printLoadingMeasure() {
        MeasurePrinter.print(getPage());
        return getPage();
    }

    @Override
    public T printLoadingMeasure(String object) {
        MeasurePrinter.print(getPage(), object);
        return getPage();
    }

    @Override
    public E2eWebElement getTarget() {
        return E2eWebElement.newInstance(body);
    }
}
