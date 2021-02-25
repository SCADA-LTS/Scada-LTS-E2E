package org.scadalts.e2e.test.impl.tests.service.eventhandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.eventhandler.EventHandlerGetParams;
import org.scadalts.e2e.service.impl.services.eventhandler.EventHandlerResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.EventHandlerUtil.createEventHandler;

public class GetEventHandlerServiceTest {

    private int dataPointId;
    private int eventDetectorId;
    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;

    @Before
    public void createDataSourceAndPoint() {
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(DataPointType.BINARY, "0");
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.openNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        EditDataSourceWithPointListPage pointPage = dataSourcePointObjectsCreator.createObjects()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier());
        dataPointId = pointPage.getDataPointDatabaseId(dataPointCriteria.getIdentifier());
        EventDetectorCriteria eventDetectorCriteria =
                EventDetectorCriteria.criteria(IdentifierObjectFactory.eventDetectorName(EventDetectorType.CHANGE), AlarmLevel.INFORMATION, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria));
        dataSourcePointObjectsCreator.createObjects();
        eventDetectorId = TestWithoutPageUtil.createEventDetectorAndGetId(dataPointId, eventDetectorCriteria);
    }


    @After
    public void clean() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_getEventHandlers_then_status_http_200() {
        //given:
        createEventHandler(Xid.xidForEventHandler(), dataPointId, eventDetectorId, EventHandlerType.EMAIL);
        createEventHandler(Xid.xidForEventHandler(), dataPointId, eventDetectorId, EventHandlerType.SMS);

        //when:
        E2eResponse<List<EventHandlerResponse>> getResponse = TestWithoutPageUtil.getEventHandlers();

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_getEmailEventHandlerByXid_then_status_http_200() {

        //given:
        Xid xid = Xid.xidForEventHandler();
        createEventHandler(xid, dataPointId, eventDetectorId, EventHandlerType.EMAIL);
        EventHandlerGetParams eventHandlerGetParams = new EventHandlerGetParams();
        eventHandlerGetParams.setXid(xid.getValue());

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerGetParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_getSmsEventHandlerByXid_then_status_http_200() {

        //given:
        Xid xid = Xid.xidForEventHandler();
        createEventHandler(xid, dataPointId, eventDetectorId, EventHandlerType.SMS);
        EventHandlerGetParams eventHandlerGetParams = new EventHandlerGetParams();
        eventHandlerGetParams.setXid(xid.getValue());

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerGetParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_getEmailEventHandlerByXid_then_check_xId() {

        //given:
        Xid xid = Xid.xidForEventHandler();
        createEventHandler(xid, dataPointId, eventDetectorId, EventHandlerType.EMAIL);
        EventHandlerGetParams eventHandlerGetParams = new EventHandlerGetParams();
        eventHandlerGetParams.setXid(xid.getValue());

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerGetParams);

        //then:
        assertEquals(xid.getValue(), getResponse.getValue().getXid());
    }

    @Test
    public void test_getSmsEventHandlerByXid_then_check_xId() {

        //given:
        Xid xid = Xid.xidForEventHandler();
        createEventHandler(xid, dataPointId, eventDetectorId, EventHandlerType.SMS);
        EventHandlerGetParams eventHandlerGetParams = new EventHandlerGetParams();
        eventHandlerGetParams.setXid(xid.getValue());

        //when:
        E2eResponse<EventHandlerResponse> getResponse = TestWithoutPageUtil.getEventHandlerByXid(eventHandlerGetParams);

        //then:
        assertEquals(xid.getValue(), getResponse.getValue().getXid());
    }

}


