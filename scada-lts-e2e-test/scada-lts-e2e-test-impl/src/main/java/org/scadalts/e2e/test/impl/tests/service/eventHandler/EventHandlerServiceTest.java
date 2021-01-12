package org.scadalts.e2e.test.impl.tests.service.eventHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorParams;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorPostResponse;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorResponse;
import org.scadalts.e2e.service.impl.services.eventHandler.EventHandlerParams;
import org.scadalts.e2e.service.impl.services.eventHandler.EventHandlerResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithPageRunner.class)
public class EventHandlerServiceTest {

    private String dataPointXid;
    private int dataPointId;
    private int eventDetectorId;
    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private EventDetectorCriteria eventDetectorCriteria;
    private String eventDetectorXid;
    private int eventDetectorAlarmLevel;
    private EventDetectorResponse eventDetectorResponse;
    private Xid xidExpected;

    @Before
    public void createDataSourceAndPoint() {
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(DataPointType.BINARY, "0");
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        EditDataSourceWithPointListPage pointPage = dataSourcePointObjectsCreator.createObjects()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier());
        dataPointId = pointPage.getDataPointDatabaseId(dataPointCriteria.getIdentifier());
        eventDetectorCriteria =
                EventDetectorCriteria.criteria(IdentifierObjectFactory.eventDetectorName(EventDetectorType.CHANGE), AlarmLevel.INFORMATION, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria));
        dataSourcePointObjectsCreator.createObjects();
        dataPointXid = dataPointCriteria.getXid().getValue();
        eventDetectorId = createEventDetector();
        xidExpected = Xid.xidForEventHandler();
        createEventHandler();
    }

    private int createEventDetector(){
        eventDetectorXid = eventDetectorCriteria.getXid().getValue();
        eventDetectorAlarmLevel = Integer.parseInt(eventDetectorCriteria.getAlarmLevel().getId());
        eventDetectorResponse =
                EventDetectorResponse.builder()
                        .xid(eventDetectorXid)
                        .alias("e2e_Test")
                        .alarmLevel(eventDetectorAlarmLevel)
                        .build();
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setXid(dataPointXid);
        eventDetectorParams.setBody(eventDetectorResponse);
        E2eResponse<EventDetectorPostResponse> setResponse = TestWithoutPageUtil.setEventDetector(eventDetectorParams);
        return setResponse.getValue().getId();
    }

    private void createEventHandler(){
        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setTypeId(1);
        eventHandlerParams.setTypeRef1(dataPointId);
        eventHandlerParams.setTypeRef2(eventDetectorId);



        EventHandlerResponse eventHandlerResponse = EventHandlerResponse.builder()
                .id(-1)
                .xid(xidExpected.getValue())
                .alias("eventhandler_test_create")
                .disabled(false)
                .activeRecipients(null)
                .sendEscalation(false)
                .escalationDelayType(1)
                .escalationDelay(0)
                .escalationRecipients(null)
                .sendInactive(false)
                .inactiveOverride(false)
                .inactiveRecipients(null)
                .build();

        eventHandlerParams.setBody(eventHandlerResponse);

        TestWithoutPageUtil.createEventHandlerTypeEmail(eventHandlerParams);
    }

    @After
    public void clean() {
        dataSourcePointObjectsCreator.deleteObjects();
    }

//    @Test
//    public void test_getEventHandlers_then_status_http_200() {
//        //given:
//
//
//        //when:
//        E2eResponse<List<EventHandlerResponse>> getResponse = TestWithoutPageUtil.getEventHandlers();
//
//        //then:
//        assertEquals(200, getResponse.getStatus());
//    }

    @Test
    public void test_getEventHandlerByXid_then_status_http_200() {

        //given:
        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setXid(xidExpected.getValue());

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

}


