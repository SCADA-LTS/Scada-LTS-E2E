package org.scadalts.e2e.page.impl.pages.scripts;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
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

    @FindBy(id = "loader")
    private SelenideElement loader;

    public static final String TITLE = "Scripts";

    public ScriptsPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public ScriptsPage waitForCompleteLoad() {
        if(loader.is(Condition.visible))
            waitWhile(loader, Condition.visible);
        return this;
    }

    public EditScriptsPage openScriptCreator() {
        delay();
        waitWhile(addScript, not(Condition.visible)).click();
        return page(EditScriptsPage.class);
    }

    public EditScriptsPage openScriptEditor(ScriptCriteria criteria) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getIdentifier(), Tag.tbody());
        findObject(nodeCriteria, scriptsTable).click();
        return page(EditScriptsPage.class);
    }

    @Override
    public boolean containsObject(IdentifierObject identifier) {
        waitForCompleteLoad();
        waitWhile(scriptsTable, not(Condition.visible));
        return findObject(identifier.getNodeCriteria(),scriptsTable).is(Condition.visible);
    }

    @Override
    public ScriptsPage getPage() {
        return this;
    }
}
