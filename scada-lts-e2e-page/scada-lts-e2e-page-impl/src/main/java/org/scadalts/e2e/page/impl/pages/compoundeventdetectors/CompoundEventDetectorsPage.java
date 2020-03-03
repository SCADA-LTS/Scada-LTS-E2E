package org.scadalts.e2e.page.impl.pages.compoundeventdetectors;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

public class CompoundEventDetectorsPage extends MainPageObjectAbstract<CompoundEventDetectorsPage> {

    public static final String TITLE = "Compound event detectors";

    public CompoundEventDetectorsPage(SelenideElement source) {
        super(source, TITLE);
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

}