package org.scadalts.e2e.page.impl.pages.sql;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class SqlPage extends MainPageObjectAbstract<SqlPage> {

    public static final String TITLE = "SQL";

    public SqlPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public SqlPage getPage() {
        return this;
    }
}