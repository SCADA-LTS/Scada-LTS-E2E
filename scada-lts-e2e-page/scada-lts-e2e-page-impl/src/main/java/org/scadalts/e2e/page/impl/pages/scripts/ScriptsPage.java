package org.scadalts.e2e.page.impl.pages.scripts;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.core.utils.RegexFactory;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public E2eWebElement getTarget() {
        return E2eWebElement.newInstance(scriptsTable);
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
    public boolean containsObject(CriteriaObject criteria) {
        String bodyText = getBodyText();
        Pattern pattern = Pattern.compile(RegexFactory.identifier(criteria.getIdentifier()));
        Matcher matcher = pattern.matcher(bodyText);
        return matcher.find();
    }

    @Override
    public ScriptsPage getPage() {
        return this;
    }
}
