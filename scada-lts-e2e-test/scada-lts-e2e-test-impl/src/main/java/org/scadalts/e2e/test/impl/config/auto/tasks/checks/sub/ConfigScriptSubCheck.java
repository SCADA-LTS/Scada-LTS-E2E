package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.scripts.ScriptsPage;
import org.scadalts.e2e.test.core.asserts.E2eAssert;

@Data
public class ConfigScriptSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull ScriptCriteria scriptCriteria;

    @Override
    public void execute() {
        ScriptsPage scriptsPage = navigationPage.openScripts();
        E2eAssert.assertExists(scriptsPage, scriptCriteria);
    }
}
