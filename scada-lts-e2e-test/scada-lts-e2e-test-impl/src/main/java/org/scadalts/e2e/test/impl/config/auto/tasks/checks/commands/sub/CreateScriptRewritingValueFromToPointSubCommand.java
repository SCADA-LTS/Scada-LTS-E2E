package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.NonNull;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.ScriptIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.VarIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.ScriptObjectsCreator;

@Builder
public class CreateScriptRewritingValueFromToPointSubCommand implements SubCommand<ScriptCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull VirtualDataSourcePointCriteria dataPointFromCriteria;
    private final @NonNull VirtualDataSourcePointCriteria dataPointToCriteria;

    @Override
    public ScriptCriteria execute() {

        VarCriteria varCriteria = new VarCriteria(new VarIdentifier("p66"));
        DataPointVarCriteria dataPointVarCriteria = new DataPointVarCriteria(dataPointFromCriteria.getIdentifier(), varCriteria);

        Script script = Script.script(_getScriptPattern(), dataPointToCriteria.getDataPoint().getXid(), varCriteria);

        ScriptCriteria scriptCriteria = ScriptCriteria
                .dataPointCommandsEnabled(new ScriptIdentifier("sc_event_detector_test"),
                        script, dataPointVarCriteria);

        ScriptObjectsCreator scriptObjectsCreator = new ScriptObjectsCreator(navigationPage, scriptCriteria);
        scriptObjectsCreator.createObjects();
        return scriptCriteria;
    }

    private String _getScriptPattern() {
        return "toSave = ''{0}''\n" +
                " \n" +
                " val_2.writeDataPoint(toSave, {1}.value);";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
