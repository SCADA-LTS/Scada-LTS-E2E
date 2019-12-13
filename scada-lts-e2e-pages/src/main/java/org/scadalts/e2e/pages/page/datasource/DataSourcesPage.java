package org.scadalts.e2e.pages.page.datasource;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class DataSourcesPage extends PageObjectAbstract<DataSourcesPage> {

    public DataSourcesPage(SelenideElement source) {
        super(source);
    }

    @Override
    public DataSourcesPage getPage() {
        return this;
    }
}