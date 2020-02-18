package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.core.util.TypeParser;
import org.scadalts.e2e.page.core.util.XpathFactory;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.not;
import static org.scadalts.e2e.page.core.util.StabilityUtil.refreshWhile;
import static org.scadalts.e2e.page.core.util.StabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.util.TypeParser.parseIntValueFormatted;

@Log4j2
public class DataPointDetailsPage extends PageObjectAbstract<DataPointDetailsPage> {


    @FindBy(css = "input[id*='txtChange']")
    private SelenideElement valueInput;

    @FindBy(css = "a[id*='txtSet']")
    private SelenideElement setButton;

    @FindBy(id = "pointValue")
    private SelenideElement valueField;

    @FindBy(id = "historyTableData")
    private SelenideElement historyTableData;

    @FindBy(id = "historyLimit")
    private SelenideElement historyLimit;


    public DataPointDetailsPage() {
        super("Statistics;History");
    }

    @Override
    public DataPointDetailsPage getPage() {
        return this;
    }

    public DataPointDetailsPage setDataPointValue(String value) {
        refreshWhile(valueInput, not(Condition.visible));
        valueInput.clear();
        valueInput.sendKeys(value);
        return this;
    }

    public DataPointDetailsPage confirmDataPointValue() {
        setButton.click();
        return this;
    }

    public String getDataPointValue() {
        return refreshWhile(valueField, Condition.empty).getText();
    }

    public List<String> getValuesFromHistory() {

        String xpath = XpathFactory.xpathEveryXElementFirst(3, "td");
        logger.info("xpath: {}", xpath);

        ElementsCollection elements = waitWhile(historyTableData, not(Condition.visible))
                .$$(By.xpath(xpath));
        logger.debug("elements: {}", elements);

        return elements.stream()
                .map(SelenideElement::getText)
                .map(TypeParser::parseIntValueFormatted)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    public int getHistoryLimit() {
        return parseIntValueFormatted(waitWhile(historyLimit, Condition.empty).getValue());
    }
}
