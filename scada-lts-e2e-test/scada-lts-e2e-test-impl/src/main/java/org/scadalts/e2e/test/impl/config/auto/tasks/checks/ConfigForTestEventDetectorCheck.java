package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.junit.Before;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.ConfigureTestEventDetectorCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigDataSourcePointSubCheck;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigEventDetectorSubCheck;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigEventHandlerSubCheck;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigScriptSubCheck;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;

import java.util.Set;

@Data
public class ConfigForTestEventDetectorCheck implements Check<EventDetectorCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Before
    public void execute() {
        CriteriaRegisterAggregator creatorObjectByTestAggregator = CriteriaRegisterAggregator.INSTANCE;

        if(!creatorObjectByTestAggregator.containsRegister(getClassTarget())) {
            ConfigureTestEventDetectorCommand configureTestEventDetectorCommand = new ConfigureTestEventDetectorCommand(navigationPage);
            configureTestEventDetectorCommand.execute();
        }

        CriteriaRegister register = creatorObjectByTestAggregator.getRegister(getClassTarget());

        Set<EventDetectorCriteria> eventDetectorCriterias = register.get(EventDetectorCriteria.class);
        for (EventDetectorCriteria eventDetectorCriteria : eventDetectorCriterias) {

            ConfigEventDetectorSubCheck checkEventDetectorSubTask = new ConfigEventDetectorSubCheck(navigationPage, eventDetectorCriteria);
            checkEventDetectorSubTask.execute();

            ConfigDataSourcePointSubCheck checkConfigDataSourcePointSubTask = new ConfigDataSourcePointSubCheck(navigationPage,
                    eventDetectorCriteria.getDataSourcePointCriteria());
            checkConfigDataSourcePointSubTask.execute();
        }

        Set<ScriptCriteria> scriptCriterias = register.get(ScriptCriteria.class);
        for(ScriptCriteria criteria: scriptCriterias) {
            ConfigScriptSubCheck checkScriptSubTask = new ConfigScriptSubCheck(navigationPage, criteria);
            checkScriptSubTask.execute();
        }

        Set<EventHandlerCriteria> eventHandlerCriterias = register.get(EventHandlerCriteria.class);
        for(EventHandlerCriteria eventHandlerCriteria : eventHandlerCriterias) {
            ConfigEventHandlerSubCheck checkEventHandlerSubTask = new ConfigEventHandlerSubCheck(navigationPage,eventHandlerCriteria);
            checkEventHandlerSubTask.execute();
        }
    }

    @Override
    public Class<EventDetectorCheckTest> getClassTarget() {
        return EventDetectorCheckTest.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
