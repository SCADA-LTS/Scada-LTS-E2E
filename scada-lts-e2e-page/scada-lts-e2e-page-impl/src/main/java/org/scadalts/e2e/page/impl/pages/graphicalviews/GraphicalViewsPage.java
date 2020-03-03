package org.scadalts.e2e.page.impl.pages.graphicalviews;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

public class GraphicalViewsPage extends MainPageObjectAbstract<GraphicalViewsPage> {

    @FindBy(css = "#graphical td select")
    private SelenideElement select;

    @FindBy(css = "a[href='view_edit.shtm']")
    private SelenideElement creator;

    public static final String TITLE = "Graphic views";

    public GraphicalViewsPage(SelenideElement source) {
        super(source, TITLE);
    }

    public EditGraphicalViewPage openViewEditor(GraphicalViewCriteria criteria) {
        return _openViewEditor(_selectViewAndGetIdByName(criteria.getIdentifier()));
    }

    public int getNumberOfViews() {
        return new Select(select)
                .getOptions()
                .size();
    }

    public Set<String> getViewNames() {
        return new Select(select)
                .getOptions()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toSet());
    }

    public Set<String> getViewIdentifiers() {
        return new Select(select)
                .getOptions()
                .stream()
                .map(Selenide::$)
                .map(SelenideElement::getValue)
                .collect(Collectors.toSet());
    }

    public Map<String, String> getDataAllViews() {
        return new Select(select)
                .getOptions()
                .stream()
                .map(Selenide::$)
                .collect(Collectors.toMap(SelenideElement::getValue, SelenideElement::getText));
    }

    public GraphicalViewsPage selectViewByName(GraphicalViewIdentifier viewName) {
        _selectViewAndGetIdByName(viewName);
        return this;
    }

    public boolean isSelectedView(GraphicalViewIdentifier viewName) {
        return select.getSelectedOption().has(Condition.text(viewName.getValue()));
    }

    public EditGraphicalViewPage openViewCreator() {
        waitWhile(creator, not(Condition.visible)).click();
        return page(new EditGraphicalViewPage(this));
    }

    public GraphicalViewsPage waitOnLoadedBackground() {
        waitWhile($(By.id("viewBackground")), not(Condition.visible));
        return this;
    }

    @Override
    public GraphicalViewsPage getPage() {
        return this;
    }

    private String _selectViewAndGetIdByName(GraphicalViewIdentifier viewName) {
        select.selectOption(viewName.getValue());
        return select.getValue();
    }

    private EditGraphicalViewPage _openViewEditor(String viewId) {
        String query = MessageFormat.format("a[href=''view_edit.shtm?viewId={0}'']", viewId);
        $(By.cssSelector(query)).click();
        return page(new EditGraphicalViewPage(this));
    }
}
