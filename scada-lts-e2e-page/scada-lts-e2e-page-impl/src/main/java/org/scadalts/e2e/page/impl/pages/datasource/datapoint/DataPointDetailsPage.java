package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.common.utils.FormatUtil;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.or;
import static org.scadalts.e2e.common.utils.FormatUtil.unformat;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObjects;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.refreshWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.utils.TypeParser.parseIntValueFormatted;

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
        valueInput.setValue(value);
        return this;
    }

    public DataPointDetailsPage confirmDataPointValue() {
        setButton.click();
        return this;
    }

    public String getDataPointValue() {
        return refreshWhile(valueField, Condition.empty).getText();
    }

    public String getDataPointValue(String expectedValue) {
        String value = unformat(expectedValue);
        SelenideElement field = refreshWhile(valueField, or("is not text: " + expectedValue, not(Condition.exactText(value))));
        String text = field.getText();
        return unformat(text);
    }

    public List<String> getValuesFromHistory() {
        return findObjects(3, historyTableData).stream()
                .map(SelenideElement::getText)
                .map(FormatUtil::unformat)
                .collect(Collectors.toList());
    }

    public int getHistoryLimit() {
        return parseIntValueFormatted(waitWhile(historyLimit, Condition.empty).getValue());
    }
}
