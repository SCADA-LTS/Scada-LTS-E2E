package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.ScriptIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.VarIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.ScriptObjectsCreator;

@Builder
public class CreateScriptSubCommand implements SubCommand<ScriptCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull DataPointCriteria dataPointCriteria;

    @Override
    public ScriptCriteria execute() {

        VarCriteria varCriteria = new VarCriteria(new VarIdentifier("p66"));
        DataPointVarCriteria dataPointVarCriteria = new DataPointVarCriteria(dataPointCriteria, varCriteria);

        Script script = new Script("toSave = ''DP_961456''\n" +
                " \n" +
                " val_2.writeDataPoint(toSave, {0}.value);", varCriteria);

        ScriptCriteria scriptCriteria = ScriptCriteria
                .dataPointCommandsEnabled(new ScriptIdentifier("sc_event_detector_test"),
                        script, dataPointVarCriteria);

        ScriptObjectsCreator scriptObjectsCreator = new ScriptObjectsCreator(navigationPage, scriptCriteria);
        scriptObjectsCreator.createObjects();
        return scriptCriteria;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
