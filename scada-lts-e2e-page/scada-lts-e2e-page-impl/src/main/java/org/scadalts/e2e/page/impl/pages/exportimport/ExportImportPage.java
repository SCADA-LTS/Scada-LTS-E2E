package org.scadalts.e2e.page.impl.pages.exportimport;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class ExportImportPage extends MainPageObjectAbstract<ExportImportPage> {

    public static final String TITLE = "Export";

    public ExportImportPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public ExportImportPage getPage() {
        return this;
    }
}