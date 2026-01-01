package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataPointVarCriteria;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.scripts.EditScriptsPage;
import org.scadalts.e2e.page.impl.pages.scripts.ScriptsPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@Log4j2
public class ScriptObjectsCreator implements CreatorObject<ScriptsPage, ScriptsPage> {

    private NavigationPage navigationPage;
    private final ScriptCriteria[] scriptCriteria;
    private ScriptsPage scriptsPage;

    public ScriptObjectsCreator(NavigationPage navigationPage, ScriptCriteria... scriptCriteria) {
        this.navigationPage = navigationPage;
        this.scriptCriteria = scriptCriteria;
    }

    @Override
    public ScriptsPage deleteObjects() {
        ScriptsPage scriptsPage = openPage();
        for (ScriptCriteria criteria: scriptCriteria) {
            if(scriptsPage.containsObject(criteria.getIdentifier())) {

                logger.info("deleting object: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getXid().getValue(), criteria.getClass().getSimpleName());

                scriptsPage.openScriptEditor(criteria)
                        .deleteScript();

                logger.info("deleted object: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getXid().getValue(), criteria.getClass().getSimpleName());
            }
        }
        return scriptsPage;
    }

    @Override
    public ScriptsPage createObjects() {
        ScriptsPage scriptsPage = openPage();
        for (ScriptCriteria criteria: scriptCriteria) {
            if(!scriptsPage.containsObject(criteria.getIdentifier())) {

                logger.info("creating object: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getXid().getValue(), criteria.getClass().getSimpleName());

                EditScriptsPage editScriptsPage = scriptsPage.openScriptCreator()
                        .setName(criteria.getIdentifier())
                        .waitOnPage(500)
                        .setXid(criteria.getXid())
                        .setDataPointCommands(criteria.isEnableDataPointCommands())
                        .setDataSourceCommands(criteria.isEnableDataSourceCommands())
                        .setScript(criteria.getScript());
                for (DataPointVarCriteria var : criteria.getDataPointVarCriterias()) {
                    editScriptsPage.addPointToContext(var.getDataSourcePointIdentifier())
                            .setVarName(var);
                }
                editScriptsPage.saveScript()
                        .containsObject(criteria.getIdentifier());
                editScriptsPage.refreshPage();
                editScriptsPage = scriptsPage.openScriptEditor(criteria);
                boolean saveRepeat = false;
                for (DataPointVarCriteria var : criteria.getDataPointVarCriterias()) {
                    if(!editScriptsPage.containsVarInContext(var)) {
                        editScriptsPage.addPointToContext(var.getDataSourcePointIdentifier())
                                .setVarName(var);
                        saveRepeat = true;
                    }
                }
                if (saveRepeat) {
                    editScriptsPage.saveScript()
                            .containsObject(criteria.getIdentifier());
                }

                logger.info("created object: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getXid().getValue(), criteria.getClass().getSimpleName());
            }
        }
        return scriptsPage.reopen();
    }

    @Override
    public ScriptsPage openPage() {
        if(scriptsPage == null) {
            scriptsPage = navigationPage.openScripts();
            return scriptsPage;
        }
        return scriptsPage.reopen();
    }

    @Override
    public void reload() {
        if(!TestWithPageUtil.isLogged())
            navigationPage = TestWithPageUtil.openNavigationPage();
    }
}
