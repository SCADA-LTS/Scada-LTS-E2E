package org.scadalts.e2e.test.impl.tests.page.eventhandlers;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventHandlerIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.ScriptIdentifier;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;
import org.scadalts.e2e.page.impl.pages.eventhandlers.EditEventHandlersPage;
import org.scadalts.e2e.page.impl.pages.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.*;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@Log4j2
public class CreateEventHandlerPageTest {

    private static EventHandlersPage eventHandlersPage;
    private static NavigationPage navigationPage;
    private static EventDetectorObjectsCreator eventDetectorObjectsCreator;
    private static ScriptObjectsCreator scriptObjectsCreator;

    private static EventDetectorObjectsCreator eventDetectorObjectsCreator2;
    private static EventDetectorObjectsCreator eventDetectorObjectsCreator3;

    private static ScriptCriteria scriptActive;
    private static ScriptCriteria scriptInactive;
    private static UpdateDataSourceCriteria dataSourceCriteria;
    private static VirtualDataPointCriteria dataPointCriteria;
    private static EventDetectorCriteria eventDetectorCriteria;
    private EventHandlerCriteria eventHandlerCriteria;


    @BeforeClass
    public static void createDataSourcePointEventDetectorScripts() {
        navigationPage = TestWithPageUtil.openNavigationPage();

        dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond();
        dataPointCriteria = VirtualDataPointCriteria.binaryNoChange();
        DataSourcePointCriteria<UpdateDataSourceCriteria,VirtualDataPointCriteria> dataSourcePointCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);

        DataSourcePointCriteria<UpdateDataSourceCriteria, VirtualDataPointCriteria> dataSourcePointCriteria1 = new DataSourcePointCriteria<>(UpdateDataSourceCriteria.virtualDataSourceSecond(),
                VirtualDataPointCriteria.binaryNoChange());

        DataSourcePointCriteria<UpdateDataSourceCriteria, VirtualDataPointCriteria> dataSourcePointCriteria3 = new DataSourcePointCriteria<>(UpdateDataSourceCriteria.virtualDataSourceSecond(),
                VirtualDataPointCriteria.binaryNoChange());

        EventDetectorCriteria eventDetectorCriteria2 = EventDetectorCriteria.changeAlarmLevelNone(new DataSourcePointCriteria<>(dataSourceCriteria, dataPointCriteria));
        eventDetectorCriteria = EventDetectorCriteria.changeAlarmLevelNone(dataSourcePointCriteria1);
        EventDetectorCriteria eventDetectorCriteria3 = EventDetectorCriteria.changeAlarmLevelNone(dataSourcePointCriteria3);

        eventDetectorObjectsCreator = new EventDetectorObjectsCreator(navigationPage, eventDetectorCriteria);
        eventDetectorObjectsCreator2 = new EventDetectorObjectsCreator(navigationPage, eventDetectorCriteria2);
        eventDetectorObjectsCreator3 = new EventDetectorObjectsCreator(navigationPage, eventDetectorCriteria3);

        eventDetectorObjectsCreator.createObjects();
        eventDetectorObjectsCreator2.createObjects();
        eventDetectorObjectsCreator3.createObjects();

        scriptActive = ScriptCriteria.dataPointCommandsEnabled(Script.empty(), DataPointVarCriteria.criteria(dataSourcePointCriteria.getIdentifier()));
        scriptInactive = ScriptCriteria.dataPointCommandsEnabled(Script.empty(), DataPointVarCriteria.criteria(dataSourcePointCriteria.getIdentifier()));

        scriptObjectsCreator = new ScriptObjectsCreator(navigationPage,scriptActive, scriptInactive);
        scriptObjectsCreator.createObjects();

        eventHandlersPage = navigationPage.openEventHandlers();
    }

    @AfterClass
    public static void cleanAfterClass() {
        if(scriptObjectsCreator != null)
            scriptObjectsCreator.deleteObjects();
        if(eventDetectorObjectsCreator != null)
            eventDetectorObjectsCreator.deleteObjects();
        if(eventDetectorObjectsCreator2 != null)
            eventDetectorObjectsCreator2.deleteObjects();
        if(eventDetectorObjectsCreator3 != null)
            eventDetectorObjectsCreator3.deleteObjects();

        if(eventDetectorObjectsCreator != null)
            eventDetectorObjectsCreator.deleteDataSources();
        if(eventDetectorObjectsCreator2 != null)
            eventDetectorObjectsCreator2.deleteDataSources();
        if(eventDetectorObjectsCreator3 != null)
            eventDetectorObjectsCreator3.deleteDataSources();
    }

    @After
    public void cleanAfter() {
        EventHandlerObjectsCreator eventHandlerObjectsCreator = new EventHandlerObjectsCreator(navigationPage, eventHandlerCriteria);
        eventHandlerObjectsCreator.deleteObjects();
    }

    @Test
    public void test_create_event_handler() {

        //given:
        Xid xidExpected = Xid.eventHandler();
        EventHandlerType eventHandlerTypeExpected = EventHandlerType.SCRIPT;
        EventHandlerIdentifier eventHandlerIdentifierExpected = IdentifierObjectFactory.eventHandlerName(eventHandlerTypeExpected);

        eventHandlerCriteria = EventHandlerCriteria.builder()
                .identifier(eventHandlerIdentifierExpected)
                .xid(xidExpected)
                .activeScript(scriptActive)
                .inactiveScript(scriptInactive)
                .eventDetectorCriteria(eventDetectorCriteria)
                .disabled(true)
                .build();

        //when:
        eventHandlersPage.openEventHandlerCreator(eventDetectorCriteria)
                .setEventHandlerType(eventHandlerTypeExpected)
                .setXid(xidExpected)
                .setAlisas(eventHandlerIdentifierExpected)
                .setDisabled(true)
                .setActiveScriptCommand(scriptActive)
                .setInactiveScriptCommand(scriptInactive)
                .saveEventHandler();

        EditEventHandlersPage page = eventHandlersPage.openEventHandlerEditor(eventHandlerCriteria);

        //and:
        ScriptIdentifier scriptActiveIdentifierResult = page.getActiveScriptCommand();
        ScriptIdentifier scriptInactiveIdentifierResult = page.getInactiveScriptCommand();
        Xid xidResult = page.getXid();
        EventHandlerIdentifier eventHandlerIdentifierResult = page.getAlias();
        boolean disabledResult = page.isDisabled();

        //then:
        assertThat(eventHandlerIdentifierResult, equalTo(eventHandlerCriteria.getIdentifier()));
        assertThat(disabledResult, equalTo(eventHandlerCriteria.isDisabled()));
        assertThat(scriptActiveIdentifierResult, equalTo(scriptActive.getIdentifier()));
        assertThat(scriptInactiveIdentifierResult, equalTo(scriptInactive.getIdentifier()));
        assertThat(xidResult, equalTo(eventHandlerCriteria.getXid()));
    }
}
