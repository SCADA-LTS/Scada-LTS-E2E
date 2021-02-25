package org.scadalts.e2e.page.impl.pages.exportimport;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class ExportImportPage extends MainPageObjectAbstract<ExportImportPage> {

    @FindBy(css = "a[href='emport.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Export";

    public ExportImportPage() {
        super(TITLE, "/emport.shtm");
    }

    @Override
    public ExportImportPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}