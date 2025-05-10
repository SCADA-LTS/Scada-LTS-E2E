package org.scadalts.e2e.page.impl.pages.scripts;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
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

    @FindBy(css = "a[href='scripting.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Scripts";

    public ScriptsPage() {
        super(TITLE,"/scripting.shtm");
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
        waitWhile(loader, Condition.visible);
        NodeCriteria nodeCriteria = criteria.getIdentifier().getNodeCriteria();
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

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}
