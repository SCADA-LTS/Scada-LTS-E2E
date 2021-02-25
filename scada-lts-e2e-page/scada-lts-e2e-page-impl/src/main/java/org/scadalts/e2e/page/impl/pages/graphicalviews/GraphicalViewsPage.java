package org.scadalts.e2e.page.impl.pages.graphicalviews;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
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

    @FindBy(css = "a[href='views.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Graphic views";

    public GraphicalViewsPage() {
        super(TITLE, "/views.shtm");
    }

    public EditGraphicalViewPage openViewEditor(GraphicalViewIdentifier identifier) {
        return _openViewEditor(_selectViewAndGetIdByName(identifier));
    }

    public EditGraphicalViewPage openViewEditorFirst(GraphicalViewIdentifier identifier) {
        return _openViewEditor(_selectViewAndGetIdByNameFirst(identifier));
    }

    public int getNumberOfViews() {
        delay();
        return new Select(select)
                .getOptions()
                .size();
    }

    public Set<String> getViewNames() {
        delay();
        return new Select(select)
                .getOptions()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toSet());
    }

    public Set<String> getViewIdentifiers() {
        delay();
        return new Select(select)
                .getOptions()
                .stream()
                .map(Selenide::$)
                .map(SelenideElement::getValue)
                .collect(Collectors.toSet());
    }

    public Map<String, String> getDataAllViews() {
        delay();
        return new Select(select)
                .getOptions()
                .stream()
                .map(Selenide::$)
                .collect(Collectors.toMap(SelenideElement::getValue, SelenideElement::getText));
    }

    public GraphicalViewsPage selectViewByName(GraphicalViewIdentifier viewName) {
        delay();
        _selectViewAndGetIdByName(viewName);
        return this;
    }

    public boolean isSelectedView(GraphicalViewIdentifier viewName) {
        delay();
        return select.getSelectedOption().has(Condition.text(viewName.getValue()));
    }

    public EditGraphicalViewPage openViewCreator() {
        delay();
        waitWhile(creator, not(Condition.visible)).click();
        return page(new EditGraphicalViewPage(this));
    }

    public GraphicalViewsPage waitOnLoadedBackground() {
        delay();
        waitWhile($(By.id("viewBackground")), not(Condition.visible));
        return this;
    }

    @Override
    public GraphicalViewsPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }

    private String _selectViewAndGetIdByName(GraphicalViewIdentifier viewName) {
        select.selectOption(viewName.getValue());
        return select.getValue();
    }

    private String _selectViewAndGetIdByNameFirst(GraphicalViewIdentifier viewName) {
        select.selectOptionContainingText(viewName.getValue());
        return select.getValue();
    }

    private EditGraphicalViewPage _openViewEditor(String viewId) {
        delay();
        String query = MessageFormat.format("a[href=''view_edit.shtm?viewId={0}'']", viewId);
        $(By.cssSelector(query)).click();
        return page(new EditGraphicalViewPage(this));
    }
}
