package org.scadalts.e2e.pages.page.graphicalviews;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

import java.text.MessageFormat;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class GraphicalViewsPage extends MainPageObjectAbstract<GraphicalViewsPage> {

    @FindBy(css = "td select")
    private SelenideElement select;

    @FindBy(css = "a[href='view_edit.shtm']")
    private SelenideElement creator;

    public final static String URL_REF = "/views.shtm";

    public GraphicalViewsPage(SelenideElement source) {
        super(source);
    }

    public static GraphicalViewsPage openPage() {
        return open(URL_REF, GraphicalViewsPage.class);
    }

    public String selectViewByName(String name) {
        select.selectOption(name);
        return select.getValue();
    }

    public EditViewPage openViewCreator() {
        creator.click();
        return page(EditViewPage.class);
    }

    public EditViewPage openViewEditor(String viewId) {
        String query = MessageFormat.format("a[href=''view_edit.shtm?viewId={0}'']", viewId);
        $(By.cssSelector(query)).click();
        return page(EditViewPage.class);
    }

    @Override
    public GraphicalViewsPage getPage() {
        return this;
    }
}
