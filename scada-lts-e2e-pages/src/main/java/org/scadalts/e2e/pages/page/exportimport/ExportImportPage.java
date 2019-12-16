package org.scadalts.e2e.pages.page.exportimport;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class ExportImportPage extends MainPageObjectAbstract<ExportImportPage> {

    public ExportImportPage(SelenideElement source) {
        super(source);
    }

    @Override
    public ExportImportPage getPage() {
        return this;
    }
}