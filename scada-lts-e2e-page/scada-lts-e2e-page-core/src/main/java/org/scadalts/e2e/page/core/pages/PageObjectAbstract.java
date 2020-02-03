package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.util.StabilityUtil;

import java.text.MessageFormat;

import static org.scadalts.e2e.common.measure.ValueTimeUnitToPrint.preparingToPrintMs;

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
    public String getTitle() {
        return title;
    }

    @Override
    public String getHeadHtml() {
        return head.innerHtml();
    }

    @Override
    public String getBodyText() {
        String bodyText = body.getText();
        if(bodyText.contains(title))
           return bodyText;
        SelenideElement element = StabilityUtil.waitWhile(body, Condition.exactTextCaseSensitive(title));
        return element.getText();
    }

    @Override
    public T printLoadingMeasure() {
        String backend = preparingToPrintMs(getPage().backendPerformanceMs());
        String frontend = preparingToPrintMs(getPage().frontendPerformanceMs());

        String msgWithMeasure = MessageFormat.format("\n\npage: {0} \nbackend: {1} \nfrontend: {2}\n",
                getPage().getClass().getSimpleName(), backend, frontend);
        logger.info(msgWithMeasure);
        return getPage();
    }

    @Override
    public T printLoadingMeasure(String object) {
        String backend = preparingToPrintMs(getPage().backendPerformanceMs());
        String frontend = preparingToPrintMs(getPage().frontendPerformanceMs());

        String msgWithMeasure = MessageFormat.format("\n\npage: {0}, object: {1} \nbackend: {2} \nfrontend: {3}\n",
                getPage().getClass().getSimpleName(), object, backend, frontend);
        logger.info(msgWithMeasure);
        return getPage();
    }
}
