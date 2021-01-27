package org.scadalts.e2e.test.impl.tests.service.eventhandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.eventhandler.EventHandlerResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;


import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.EventHandlerUtil.createEventHandler;

@RunWith(TestWithPageRunner.class)
public class EventHandlerServicePostTest {

    private int dataPointId;
    private int eventDetectorId;
    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;

    @Before
    public void createDataSourceAndPoint() {
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(DataPointType.BINARY, "0");
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        EditDataSourceWithPointListPage pointPage = dataSourcePointObjectsCreator.createObjects()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier());
        dataPointId = pointPage.getDataPointDatabaseId(dataPointCriteria.getIdentifier());
        EventDetectorCriteria eventDetectorCriteria = EventDetectorCriteria.criteria(IdentifierObjectFactory.eventDetectorName(EventDetectorType.CHANGE), AlarmLevel.INFORMATION, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria));
        dataSourcePointObjectsCreator.createObjects();
        eventDetectorId = TestWithoutPageUtil.createEventDetectorAndGetId(dataPointId, eventDetectorCriteria);
    }

    @After
    public void clean() {
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_post_EmailEventHandler_then_status_http_200() {
        //given
        Xid xid = Xid.xidForEventHandler();

        //when
        E2eResponse<EventHandlerResponse> setResponse = createEventHandler(xid, dataPointId, eventDetectorId, EventHandlerType.EMAIL);

        //then
        assertEquals(200, setResponse.getStatus());
    }

    @Test
    public void test_post_SmsEventHandler_then_status_http_200() {
        //given
        Xid xid = Xid.xidForEventHandler();

        //when
        E2eResponse<EventHandlerResponse> setResponse = createEventHandler(xid, dataPointId, eventDetectorId, EventHandlerType.SMS);

        //then
        assertEquals(200, setResponse.getStatus());
    }

    @Test
    public void test_post_EmailEventHandler_then_getXid() {
        //given
        Xid xid = Xid.xidForEventHandler();

        //when
        E2eResponse<EventHandlerResponse> setResponse = createEventHandler(xid, dataPointId, eventDetectorId, EventHandlerType.EMAIL);

        //then
        assertEquals(xid.getValue(), setResponse.getValue().getXid());
    }

    @Test
    public void test_post_SmsEventHandler_then_getXid() {
        //given
        Xid xid = Xid.xidForEventHandler();

        //when
        E2eResponse<EventHandlerResponse> setResponse = createEventHandler(xid, dataPointId, eventDetectorId, EventHandlerType.SMS);

        //then
        assertEquals(xid.getValue(), setResponse.getValue().getXid());
    }

}

