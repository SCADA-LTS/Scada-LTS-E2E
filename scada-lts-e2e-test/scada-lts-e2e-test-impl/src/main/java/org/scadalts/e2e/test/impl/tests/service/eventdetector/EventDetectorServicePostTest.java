package org.scadalts.e2e.test.impl.tests.service.eventdetector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorParams;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorPostResponse;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithPageRunner.class)
public class EventDetectorServicePostTest {

    private String dataPointXid;
    private String eventDetectorXid;
    private int eventDetectorAlarmLevel;
    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private EventDetectorCriteria eventDetectorCriteria;
    private EventDetectorResponse eventDetectorResponse;

    @Before
    public void createDataSourceAndPoint() {
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(DataPointType.BINARY, "0");
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        eventDetectorCriteria =
                EventDetectorCriteria.criteria(IdentifierObjectFactory.eventDetectorName(EventDetectorType.CHANGE), AlarmLevel.INFORMATION, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria));
        dataSourcePointObjectsCreator.createObjects();
        dataPointXid = dataPointCriteria.getXid().getValue();
        eventDetectorXid = eventDetectorCriteria.getXid().getValue();
        eventDetectorAlarmLevel = Integer.parseInt(eventDetectorCriteria.getAlarmLevel().getId());
        eventDetectorResponse =
                EventDetectorResponse.builder()
                        .xid(eventDetectorXid)
                        .alias("e2e_Test")
                        .alarmLevel(eventDetectorAlarmLevel)
                        .build();
    }

    @After
    public void clean() {
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_post_eventDetector_then_status_http_200() {

        //given:
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setXid(dataPointXid);
        eventDetectorParams.setBody(eventDetectorResponse);

        //when:
        E2eResponse<EventDetectorPostResponse> setResponse = TestWithoutPageUtil.setEventDetector(eventDetectorParams);

        //then:
        assertEquals(200, setResponse.getStatus());
    }

    @Test
    public void test_post_eventDetector_getAlias() {

        //given:
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setXid(dataPointXid);
        eventDetectorParams.setBody(eventDetectorResponse);

        //when:
        E2eResponse<EventDetectorPostResponse> setResponse = TestWithoutPageUtil.setEventDetector(eventDetectorParams);

        //then:
        assertEquals("e2e_Test", setResponse.getValue().getAlias());
    }

    @Test
    public void test_post_eventDetector_getXid() {

        //given:
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setXid(dataPointXid);
        eventDetectorParams.setBody(eventDetectorResponse);

        //when:
        E2eResponse<EventDetectorPostResponse> setResponse = TestWithoutPageUtil.setEventDetector(eventDetectorParams);

        //then:
        assertEquals(eventDetectorXid, setResponse.getValue().getXid());
    }
}
