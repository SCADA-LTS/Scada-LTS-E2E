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
import static org.scadalts.e2e.test.impl.utils.EventHandlerUtil.createEventHandler;

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

    @After
    public void clean() {
        dataSourcePointObjectsCreator.deleteObjects();
    }

//    @Test
//    public void test_getEventHandlers_then_status_http_200() {
//        //given:
//        createEventHandler(Xid.xidForEventHandler().getValue(), dataPointId, eventDetectorId, 2);
//        createEventHandler(Xid.xidForEventHandler().getValue(), dataPointId, eventDetectorId, 5);
//
//        //when:
//        E2eResponse<List<EventHandlerResponse>> getResponse = TestWithoutPageUtil.getEventHandlers();
//
//        //then:
//        assertEquals(200, getResponse.getStatus());
//    }

    @Test
    public void test_getEmailEventHandlerByXid_then_status_http_200() {

        //given:
        String xid = Xid.xidForEventHandler().getValue();
        createEventHandler(xid, dataPointId, eventDetectorId, 2);
        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setXid(xid);

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_getSmsEventHandlerByXid_then_status_http_200() {

        //given:
        String xid = Xid.xidForEventHandler().getValue();
        createEventHandler(xid, dataPointId, eventDetectorId, 5);
        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setXid(xid);

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_getEmailEventHandlerByXid_then_check_xId() {

        //given:
        String xid = Xid.xidForEventHandler().getValue();
        createEventHandler(xid, dataPointId, eventDetectorId, 2);
        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setXid(xid);

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerParams);

        //then:
        assertEquals(xid, getResponse.getValue().getXid());
    }

    @Test
    public void test_getSmsEventHandlerByXid_then_check_xId() {

        //given:
        String xid = Xid.xidForEventHandler().getValue();
        createEventHandler(xid, dataPointId, eventDetectorId, 5);
        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setXid(xid);

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerParams);

        //then:
        assertEquals(xid, getResponse.getValue().getXid());
    }

}


