package org.scadalts.e2e.test.impl.tests.service.eventHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventHandlerIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.VarIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorParams;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorResponse;
import org.scadalts.e2e.service.impl.services.eventHandler.EventHandlerParams;
import org.scadalts.e2e.service.impl.services.eventHandler.EventHandlerResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.EventDetectorObjectsCreator;
import org.scadalts.e2e.test.impl.creators.EventHandlerObjectsCreator;
import org.scadalts.e2e.test.impl.creators.ScriptObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithPageRunner.class)
public class EventHandlerServiceTest {

    private String dataPointXid;
    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private EventDetectorObjectsCreator eventDetectorObjectsCreator;
    private EventDetectorCriteria eventDetectorCriteria;
    private EventHandlerCriteria eventHandlerCriteria;
    private EventHandlerObjectsCreator eventHandlerObjectsCreator;
    private static ScriptCriteria scriptActive;
    private static ScriptCriteria scriptInactive;
    private static ScriptObjectsCreator scriptObjectsCreator;

    @Before
    public void createDataSourceAndPoint() {
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(DataPointType.BINARY, "0");
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        eventDetectorCriteria =
                EventDetectorCriteria.criteria(IdentifierObjectFactory.eventDetectorName(EventDetectorType.CHANGE), AlarmLevel.INFORMATION, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria));
        eventDetectorObjectsCreator = new EventDetectorObjectsCreator(TestWithPageUtil.getNavigationPage(), eventDetectorCriteria);
        dataSourcePointObjectsCreator.createObjects();
        eventDetectorObjectsCreator.createObjects();

        Xid xidExpected = Xid.xidForEventHandler();
        EventHandlerType eventHandlerTypeExpected = EventHandlerType.SCRIPT;
        EventHandlerIdentifier eventHandlerIdentifierExpected = new EventHandlerIdentifier("eventhandler_test_create", eventHandlerTypeExpected);

        scriptActive = ScriptCriteria.dataPointCommandsEnabled(Script.empty(), new DataPointVarCriteria(dataPointCriteria, new VarCriteria(new VarIdentifier("abc"))));
        scriptInactive = ScriptCriteria.dataPointCommandsEnabled(Script.empty(), DataPointVarCriteria.criteria(dataPointCriteria));

        scriptObjectsCreator = new ScriptObjectsCreator(TestWithPageUtil.getNavigationPage(),scriptActive, scriptInactive);
        scriptObjectsCreator.createObjects();
        eventHandlerCriteria = EventHandlerCriteria.builder()
                .identifier(eventHandlerIdentifierExpected)
                .xid(xidExpected)
                .activeScript(scriptActive)
                .inactiveScript(scriptInactive)
                .eventDetectorCriteria(eventDetectorCriteria)
                .disabled(true)
                .build();

        eventHandlerObjectsCreator = new EventHandlerObjectsCreator(TestWithPageUtil.getNavigationPage(), eventHandlerCriteria);
        eventHandlerObjectsCreator.createObjects();
        dataPointXid = dataPointCriteria.getXid().getValue();
    }

    @After
    public void clean() {
        scriptObjectsCreator.deleteObjects();
        eventHandlerObjectsCreator.deleteObjects();
        eventDetectorObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_getEventHandlers_then_status_http_200() {
        //given:


        //when:
        E2eResponse<List<EventHandlerResponse>> getResponse = TestWithoutPageUtil.getEventHandlers();

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_getEventHandlerByXid_then_status_http_200() {

        //given:
        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setXid(eventHandlerCriteria.getXid().getValue());

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

}


