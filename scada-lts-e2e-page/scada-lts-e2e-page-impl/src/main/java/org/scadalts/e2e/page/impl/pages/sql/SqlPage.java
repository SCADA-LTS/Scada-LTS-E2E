package org.scadalts.e2e.page.impl.pages.sql;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class SqlPage extends MainPageObjectAbstract<SqlPage> {

    @FindBy(css = "a[href='sql.shtm']")
    private SelenideElement source;

    public static final String TITLE = "SQL";

    public SqlPage() {
        super(TITLE, "/sql.shtm");
    }

    @Override
    public SqlPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}