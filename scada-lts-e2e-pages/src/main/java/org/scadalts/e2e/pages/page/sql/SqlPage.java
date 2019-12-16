package org.scadalts.e2e.pages.page.sql;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class SqlPage extends MainPageObjectAbstract<SqlPage> {

    public SqlPage(SelenideElement source) {
        super(source);
    }

    @Override
    public SqlPage getPage() {
        return this;
    }
}