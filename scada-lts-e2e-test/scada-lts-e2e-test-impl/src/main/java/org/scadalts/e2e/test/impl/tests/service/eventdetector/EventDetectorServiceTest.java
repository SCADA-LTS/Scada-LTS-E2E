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
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.EventDetectorObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithPageRunner.class)
public class EventDetectorServiceTest {

    private Xid dataPointXid;
    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private EventDetectorObjectsCreator eventDetectorObjectsCreator;
    private EventDetectorCriteria eventDetectorCriteria;

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
        dataPointXid = dataPointCriteria.getXid();
    }

    @After
    public void clean() {
        eventDetectorObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_getEventDetectors_for_data_point_then_status_http_200() {

        //given:
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setXid(dataPointXid.getValue());

        //when:
        E2eResponse<List<EventDetectorResponse>> getResponse = TestWithoutPageUtil.getEventDetectors(eventDetectorParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_response_alarm_level_then_EventDetectorType_for_data_point() {

        //given:
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setXid(dataPointXid.getValue());

        //when:
        E2eResponse<List<EventDetectorResponse>> getResponse = TestWithoutPageUtil.getEventDetectors(eventDetectorParams);

        //then:
        assertEquals(Integer.parseInt(AlarmLevel.INFORMATION.getId()), getResponse.getValue().get(0).getAlarmLevel());
    }

    @Test
    public void test_response_type_then_EventDetectorType_for_data_point() {

        //given:
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setXid(dataPointXid.getValue());

        //when:
        E2eResponse<List<EventDetectorResponse>> getResponse = TestWithoutPageUtil.getEventDetectors(eventDetectorParams);

        //then:
        assertEquals(Integer.parseInt(EventDetectorType.CHANGE.getId()), getResponse.getValue().get(0).getDetectorType());
    }
}


