package org.scadalts.e2e.test.impl.tests.service.eventHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventHandlerIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
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

    @Before
    public void createDataSourceAndPoint() {
        DataPointCriteria dataPointCriteria = DataPointCriteria.binaryAlternate();
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        eventDetectorCriteria =
                EventDetectorCriteria.criteria(IdentifierObjectFactory.eventDetectorName(EventDetectorType.CHANGE), AlarmLevel.INFORMATION, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria));
        eventDetectorObjectsCreator = new EventDetectorObjectsCreator(TestWithPageUtil.getNavigationPage(), eventDetectorCriteria);
        Xid xidExpected = Xid.xidForEventHandler();
        EventHandlerType eventHandlerTypeExpected = EventHandlerType.SCRIPT;
        EventHandlerIdentifier eventHandlerIdentifierExpected = new EventHandlerIdentifier("eventhandler_test_create", eventHandlerTypeExpected);
        eventHandlerCriteria = EventHandlerCriteria.builder()
                .identifier(eventHandlerIdentifierExpected)
                .xid(xidExpected)
                .activeScript(ScriptCriteria.empty())
                .inactiveScript(ScriptCriteria.empty())
                .eventDetectorCriteria(eventDetectorCriteria)
                .disabled(true)
                .build();
        dataSourcePointObjectsCreator.createObjects();
        eventDetectorObjectsCreator.createObjects();
        eventHandlerObjectsCreator = new EventHandlerObjectsCreator(TestWithPageUtil.getNavigationPage(), eventHandlerCriteria);
        eventHandlerObjectsCreator.createObjects();
        dataPointXid = dataPointCriteria.getXid().getValue();
    }

    @After
    public void clean() {
        eventHandlerObjectsCreator.deleteObjects();
        eventDetectorObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_getEventHandlers_then_status_http_200() {

        //when:
        E2eResponse<List<EventHandlerResponse>> getResponse = TestWithoutPageUtil.getEventHandlers();

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_eventHandler_type() {

        //when:
        E2eResponse<List<EventHandlerResponse>> getResponse = TestWithoutPageUtil.getEventHandlers();

        //then:
        assertEquals(Integer.parseInt(EventHandlerType.SCRIPT.getId()), getResponse.getValue().get(0).getHandlerType());
    }

    @Test
    public void test_getEventHandlerByXid_then_status_http_200() {

        //given:
        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setXid(eventDetectorCriteria.getXid().getValue());

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_createEventHandlerTypeScript_then_status_http_200() {

        //given:
        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setTypeId(1);
//        eventHandlerParams.setTypeRef1();
//        eventHandlerParams.setTypeRef2();

        //when:
        E2eResponse<String> setResponse = TestWithoutPageUtil.createEventHandlerTypeScript(eventHandlerParams);

        //then:
        assertEquals(200, setResponse.getStatus());
    }




}


