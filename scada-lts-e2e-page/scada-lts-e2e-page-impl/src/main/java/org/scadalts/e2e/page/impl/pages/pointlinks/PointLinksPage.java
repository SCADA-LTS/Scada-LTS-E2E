package org.scadalts.e2e.page.impl.pages.pointlinks;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.core.util.RegexFactory;
import org.scadalts.e2e.page.impl.criteria.PointLinkCriteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.util.StabilityUtil.reloadElement;
import static org.scadalts.e2e.page.core.util.StabilityUtil.waitWhile;

@Log4j2
public class PointLinksPage extends MainPageObjectAbstract<PointLinksPage> {

    @FindBy(id = "pl-1Img")
    private SelenideElement addPointLink;

    @FindBy(id = "pointLinksTable")
    private SelenideElement pointLinksTable;

    public static final String TITLE = "Point links";

    public PointLinksPage(SelenideElement source) {
        super(source, TITLE);
    }

    public PointLinksDetailsPage openPointLinkCreator() {
        addPointLink.click();
        return page(new PointLinksDetailsPage(this));
    }

    public String getPointLinksTableText() {
        waitWhile(pointLinksTable, not(Condition.visible));
        String text = pointLinksTable.getText();
        if(StringUtils.isBlank(text))
            reloadElement(pointLinksTable);
        return pointLinksTable.getText();
    }

    public String getPointLinksTableHtml() {
        return pointLinksTable.innerHtml();
    }

    public PointLinksDetailsPage openPointLinkEditor(PointLinkCriteria criteria) {
        _findAction(criteria).click();
        return page(new PointLinksDetailsPage(this));
    }

    @Override
    public PointLinksPage getPage() {
        return this;
    }

    @Override
    public boolean containsObject(ObjectCriteria criteria) {
        String bodyText = getPointLinksTableText();
        String regex = RegexFactory.all(criteria);
        logger.info("regex: {}", regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bodyText);
        return matcher.find();
    }

    private SelenideElement _findAction(PointLinkCriteria pointLinkCriteria) {
        String xpath = pointLinkCriteria.getXpath();
        logger.info("xpath: {}", xpath);
        return pointLinksTable.$(By.xpath(xpath));
    }

}