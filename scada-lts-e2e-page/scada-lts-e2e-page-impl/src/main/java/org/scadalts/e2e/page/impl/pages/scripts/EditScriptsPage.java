package org.scadalts.e2e.page.impl.pages.scripts;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataPointVarCriteria;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.core.criterias.identifiers.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.ScriptIdentifier;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findAction;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

public class EditScriptsPage extends PageObjectAbstract<EditScriptsPage> {

    @FindBy(id = "name")
    private SelenideElement name;

    @FindBy(id = "xid")
    private SelenideElement xid;

    @FindBy(id = "allPointsList")
    private SelenideElement allPointsList;

    @FindBy(id = "allPointsList_chosen")
    private SelenideElement allPointsListChosen;

    @FindBy(id = "1ObjectAdd")
    private SelenideElement dataSourceCommands;

    @FindBy(id = "2ObjectAdd")
    private SelenideElement dataPointCommands;

    @FindBy(id = "script")
    private SelenideElement scriptTextArea;

    @FindBy(css = "img[onclick*='addPointToContext']")
    private SelenideElement addPointToContext;

    @FindBy(id = "contextContainer")
    private SelenideElement contextContainer;

    @FindBy(css = "img[onclick*='saveScript']")
    private SelenideElement saveScript;

    @FindBy(id = "deleteScriptImg")
    private SelenideElement deleteScript;

    public static final String TITLE = "Scripts";

    private static final By INPUT_VAL_NAME = By.cssSelector("input[type='text']");

    public EditScriptsPage() {
        super(TITLE);
    }


    public EditScriptsPage setName(ScriptIdentifier scriptName) {
        delay();
        waitWhile(name, not(Condition.visible)).setValue(scriptName.getValue());
        return this;
    }

    public EditScriptsPage setXid(Xid scriptXid) {
        delay();
        waitWhile(xid, not(Condition.visible)).setValue(scriptXid.getValue());
        return this;
    }

    public EditScriptsPage setDataSourceCommands(boolean enable) {
        delay();
        if(enable)
            waitWhile(dataSourceCommands, not(Condition.visible)).click();
        return this;
    }

    public EditScriptsPage setDataPointCommands(boolean enable) {
        delay();
        if(enable)
            waitWhile(dataPointCommands, not(Condition.visible)).click();
        return this;
    }

    public EditScriptsPage setScript(Script script) {
        delay();
        waitWhile(scriptTextArea, not(Condition.visible)).setValue(script.getContent());
        return this;
    }

    public EditScriptsPage addPointToContext(DataPointCriteria dataPointName) {
        _selectPoint(dataPointName);
        waitWhile(addPointToContext, not(Condition.visible)).click();
        return this;
    }

    public EditScriptsPage setVarName(DataPointVarCriteria criteria) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactlyTypeAny(criteria.getDataPointCriteria().getIdentifier(), Tag.tr());
        findAction(nodeCriteria, INPUT_VAL_NAME, contextContainer).setValue(criteria.getVarCriteria().getIdentifier().getValue());
        return this;
    }

    public EditScriptsPage saveScript() {
        delay();
        waitWhile(saveScript, not(Condition.visible)).click();
        return this;
    }

    public EditScriptsPage deleteScript() {
        delay();
        waitWhile(deleteScript, not(Condition.visible)).click();
        return this;
    }

    @Override
    public EditScriptsPage getPage() {
        return this;
    }

    private EditScriptsPage _selectPoint(DataPointCriteria dataPointName) {
        delay();
        waitWhile(allPointsListChosen, not(Condition.visible)).click();
        NodeCriteria nodeCriteria = NodeCriteria.exactlyTypeAny(dataPointName.getIdentifier(), Tag.li());
        findObject(nodeCriteria, $(By.cssSelector(".chosen-results"))).click();
        return this;
    }
}
