package org.scadalts.e2e.page.impl.pages.scripts;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.RowCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

public class ScriptsPage extends MainPageObjectAbstract<ScriptsPage> {

    @FindBy(id = "se-1Img")
    private SelenideElement addScript;

    @FindBy(id = "scriptsTable")
    private SelenideElement scriptsTable;

    public static final String TITLE = "Scripts";

    public ScriptsPage(SelenideElement source) {
        super(source, TITLE);
    }

    public EditScriptsPage openScriptCreator() {
        waitWhile(addScript, not(Condition.visible)).click();
        return page(EditScriptsPage.class);
    }

    public EditScriptsPage openScriptEditor(ScriptCriteria criteria) {
        RowCriteria rowCriteria = RowCriteria.criteria(criteria.getIdentifier(), Tag.tbody());
        findObject(rowCriteria, scriptsTable).click();
        return page(EditScriptsPage.class);
    }

    @Override
    public ScriptsPage getPage() {
        return this;
    }
}
