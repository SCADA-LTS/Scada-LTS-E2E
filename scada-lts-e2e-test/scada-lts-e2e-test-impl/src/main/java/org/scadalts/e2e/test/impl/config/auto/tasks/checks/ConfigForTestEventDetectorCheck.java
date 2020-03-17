package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.junit.Before;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.ConfigureTestEventDetectorCommand;
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

        CriteriaRegister register = CriteriaRegister.getRegister(getClassTest(), new ConfigureTestEventDetectorCommand(navigationPage));

        Set<DataSourcePointCriteria> dataSourcePointCriterias = register.get(DataSourcePointCriteria.class);
        ConfigDataSourcePointSubCheck checkConfigDataSourcePointSubTask = new ConfigDataSourcePointSubCheck(navigationPage, dataSourcePointCriterias);
        checkConfigDataSourcePointSubTask.check();

        Set<EventDetectorCriteria> eventDetectorCriterias = register.get(EventDetectorCriteria.class);
        ConfigEventDetectorSubCheck checkEventDetectorSubTask = new ConfigEventDetectorSubCheck(navigationPage, eventDetectorCriterias);
        checkEventDetectorSubTask.check();

        Set<ScriptCriteria> scriptCriterias = register.get(ScriptCriteria.class);
        ConfigScriptSubCheck checkScriptSubTask = new ConfigScriptSubCheck(navigationPage, scriptCriterias);
        checkScriptSubTask.check();

        Set<EventHandlerCriteria> eventHandlerCriterias = register.get(EventHandlerCriteria.class);
        ConfigEventHandlerSubCheck checkEventHandlerSubTask = new ConfigEventHandlerSubCheck(navigationPage, eventHandlerCriterias);
        checkEventHandlerSubTask.check();

    }

    @Override
    public Class<EventDetectorCheckTest> getClassTest() {
        return EventDetectorCheckTest.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
