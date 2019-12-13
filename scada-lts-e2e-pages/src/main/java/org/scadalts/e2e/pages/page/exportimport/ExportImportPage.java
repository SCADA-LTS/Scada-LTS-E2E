package org.scadalts.e2e.pages.page.exportimport;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class ExportImportPage extends PageObjectAbstract<ExportImportPage> {

    public ExportImportPage(SelenideElement source) {
        super(source);
    }

    @Override
    public ExportImportPage getPage() {
        return this;
    }
}