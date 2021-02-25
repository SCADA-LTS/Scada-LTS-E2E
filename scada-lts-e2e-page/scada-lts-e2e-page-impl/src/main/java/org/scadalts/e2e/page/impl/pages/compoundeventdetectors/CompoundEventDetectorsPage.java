package org.scadalts.e2e.page.impl.pages.compoundeventdetectors;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

public class CompoundEventDetectorsPage extends MainPageObjectAbstract<CompoundEventDetectorsPage> {

    @FindBy(css = "a[href='compound_events.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Compound event detectors";

    public CompoundEventDetectorsPage() {
        super(TITLE, "/compound_events.shtm");
    }

    @Override
    public CompoundEventDetectorsPage getPage() {
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
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }

}