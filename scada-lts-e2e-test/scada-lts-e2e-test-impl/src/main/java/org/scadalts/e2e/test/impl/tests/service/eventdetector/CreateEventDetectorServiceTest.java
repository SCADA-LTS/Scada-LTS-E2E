package org.scadalts.e2e.test.impl.tests.service.eventdetector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorParams;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorPostResponse;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import static org.junit.Assert.assertEquals;

public class CreateEventDetectorServiceTest {

    private Xid dataPointXid;
    private int dataPointId;
    private Xid eventDetectorXid;
    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private EventDetectorResponse eventDetectorResponse;
    private String eventDetectorName;

    @Before
    public void createDataSourceAndPoint() {
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(DataPointType.BINARY, "0");
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.openNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        EditDataSourceWithPointListPage pointPage = dataSourcePointObjectsCreator.createObjects()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier());

        EventDetectorCriteria eventDetectorCriteria = EventDetectorCriteria.criteria(
                IdentifierObjectFactory.eventDetectorName(EventDetectorType.CHANGE),
                AlarmLevel.INFORMATION,
                DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria));

        dataPointId = pointPage.getDataPointDatabaseId(dataPointCriteria.getIdentifier());
        dataPointXid = dataPointCriteria.getXid();
        eventDetectorXid = eventDetectorCriteria.getXid();
        int eventDetectorAlarmLevel = Integer.parseInt(eventDetectorCriteria.getAlarmLevel().getId());
        eventDetectorName = eventDetectorCriteria.getIdentifier().getValue();
        eventDetectorResponse =
                EventDetectorResponse.builder()
                        .xid(eventDetectorXid.getValue())
                        .alias(eventDetectorName)
                        .alarmLevel(eventDetectorAlarmLevel)
                        .build();
    }

    @After
    public void clean() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_post_eventDetector_then_status_http_200() {

        //given:
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setId(dataPointId);
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
        eventDetectorParams.setId(dataPointId);
        eventDetectorParams.setBody(eventDetectorResponse);

        //when:
        E2eResponse<EventDetectorPostResponse> setResponse = TestWithoutPageUtil.setEventDetector(eventDetectorParams);

        //then:
        assertEquals(eventDetectorName, setResponse.getValue().getAlias());
    }

    @Test
    public void test_post_eventDetector_getXid() {

        //given:
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setId(dataPointId);
        eventDetectorParams.setBody(eventDetectorResponse);

        //when:
        E2eResponse<EventDetectorPostResponse> setResponse = TestWithoutPageUtil.setEventDetector(eventDetectorParams);

        //then:
        assertEquals(eventDetectorXid.getValue(), setResponse.getValue().getXid());
    }
}
