package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.scripts.ScriptsPage;
import org.scadalts.e2e.test.core.asserts.E2eAssert;

import java.util.Set;

@Data
public class ConfigScriptSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<ScriptCriteria> scriptCriterias;

    @Override
    public void check() {
        ScriptsPage scriptsPage = navigationPage.openScripts();
        for (ScriptCriteria scriptCriteria: scriptCriterias) {
            E2eAssert.assertExists(scriptsPage, scriptCriteria);
        }
    }
}