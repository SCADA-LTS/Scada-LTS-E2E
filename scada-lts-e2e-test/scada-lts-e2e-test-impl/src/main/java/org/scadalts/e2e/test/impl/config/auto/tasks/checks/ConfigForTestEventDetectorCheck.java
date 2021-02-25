package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.junit.Before;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.scripts.ScriptsPage;
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

        CriteriaRegister register = _getCriteriaRegister();
        Set<DataSourcePointCriteria> dataSourcePointCriterias = register.get(DataSourcePointCriteria.class);

        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        for(DataSourcePointCriteria dataSourcePointCriteria: dataSourcePointCriterias) {
            if (!dataSourcesPage.containsObject(dataSourcePointCriteria.getDataSource().getIdentifier())) {
                CriteriaRegister.removeRegister(getClassTest());
                _getCriteriaRegister();
            }
        }

        Set<EventDetectorCriteria> eventDetectorCriterias = register.get(EventDetectorCriteria.class);

        for(EventDetectorCriteria eventDetectorCriteria: eventDetectorCriterias) {
            DataSourcePointCriteria dataSourcePointCriteria = eventDetectorCriteria.getDataSourcePointCriteria();

            DataPointPropertiesPage dataPointPropertiesPage = dataSourcesPage.reopen()
                    .openDataSourceEditor(dataSourcePointCriteria.getDataSource().getIdentifier())
                    .openDataPointProperties(dataSourcePointCriteria.getDataPoint().getIdentifier())
                    .waitOnEventDetectorTable();

            if (!dataPointPropertiesPage.containsObject(eventDetectorCriteria.getIdentifier())) {
                CriteriaRegister.removeRegister(getClassTest());
                _getCriteriaRegister();
            }
        }

        Set<ScriptCriteria> scriptCriterias = register.get(ScriptCriteria.class);
        ScriptsPage scriptsPage = navigationPage.openScripts();
        for(ScriptCriteria scriptCriteria: scriptCriterias) {
            if(!scriptsPage.containsObject(scriptCriteria.getIdentifier())) {
                CriteriaRegister.removeRegister(getClassTest());
                _getCriteriaRegister();
            }
        }

        Set<EventHandlerCriteria> eventHandlerCriterias = register.get(EventHandlerCriteria.class);
        EventHandlersPage eventHandlersPage = navigationPage.openEventHandlers();
        for(EventHandlerCriteria eventHandlerCriteria: eventHandlerCriterias) {
            if(!eventHandlersPage.containsObject(eventHandlerCriteria)) {
                CriteriaRegister.removeRegister(getClassTest());
                _getCriteriaRegister();
            }
        }

        ConfigDataSourcePointSubCheck checkConfigDataSourcePointSubTask = new ConfigDataSourcePointSubCheck(navigationPage, dataSourcePointCriterias);
        checkConfigDataSourcePointSubTask.check();

        ConfigEventDetectorSubCheck checkEventDetectorSubTask = new ConfigEventDetectorSubCheck(navigationPage, eventDetectorCriterias);
        checkEventDetectorSubTask.check();

        ConfigScriptSubCheck checkScriptSubTask = new ConfigScriptSubCheck(navigationPage, scriptCriterias);
        checkScriptSubTask.check();

        ConfigEventHandlerSubCheck checkEventHandlerSubTask = new ConfigEventHandlerSubCheck(navigationPage, eventHandlerCriterias);
        checkEventHandlerSubTask.check();

    }

    private CriteriaRegister _getCriteriaRegister() {
        return CriteriaRegister.getRegister(getClassTest(), new ConfigureTestEventDetectorCommand(navigationPage));
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
