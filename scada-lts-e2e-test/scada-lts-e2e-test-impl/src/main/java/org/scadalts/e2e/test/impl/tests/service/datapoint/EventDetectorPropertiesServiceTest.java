package org.scadalts.e2e.test.impl.tests.service.datapoint;

import lombok.extern.log4j.Log4j2;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.datapoint.DataPointPropertiesResponse;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.test.impl.creators.DataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.EventDetectorObjectsCreator;
import org.scadalts.e2e.test.impl.utils.DataPointPropertiesAdapter;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(Parameterized.class)
public class EventDetectorPropertiesServiceTest {

    private static DataSourceCriteria DATA_SOURCE_CRITERIA = DataSourceCriteria.virtualDataSourceSecond();
    private static DataPointCriteria DATA_POINT_CRITERIA = DataPointCriteria.numericNoChange();
    private static DataSourcePointCriteria DATA_SOURCE_POINT_CRITERIA = DataSourcePointCriteria.criteria(DATA_SOURCE_CRITERIA, DATA_POINT_CRITERIA);


    @Parameterized.Parameters(name = "number of test: {index}, detector: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {Arrays.asList(new EventDetectorCriteria[] {
                        EventDetectorCriteria.criteria(EventDetectorType.HIGH_LIMIT, DATA_SOURCE_POINT_CRITERIA, AlarmLevel.LIFE_SAFETY),
                        EventDetectorCriteria.criteria(EventDetectorType.LOW_LIMIT, DATA_SOURCE_POINT_CRITERIA, AlarmLevel.LIFE_SAFETY),
                        EventDetectorCriteria.criteria(EventDetectorType.NEGATIVE_CUSUM, DATA_SOURCE_POINT_CRITERIA, AlarmLevel.LIFE_SAFETY),
                        EventDetectorCriteria.criteria(EventDetectorType.POSITIVE_CUSUM, DATA_SOURCE_POINT_CRITERIA, AlarmLevel.LIFE_SAFETY),
                        EventDetectorCriteria.criteria(EventDetectorType.NO_CHANGE, DATA_SOURCE_POINT_CRITERIA, AlarmLevel.LIFE_SAFETY),
                        EventDetectorCriteria.criteria(EventDetectorType.NO_UPDATE, DATA_SOURCE_POINT_CRITERIA, AlarmLevel.LIFE_SAFETY),
                })},
                {Arrays.asList(new EventDetectorCriteria[] {
                        EventDetectorCriteria.change(DATA_SOURCE_POINT_CRITERIA, AlarmLevel.CRITICAL),
                        EventDetectorCriteria.change(DATA_SOURCE_POINT_CRITERIA, AlarmLevel.INFORMATION),
                        EventDetectorCriteria.change(DATA_SOURCE_POINT_CRITERIA, AlarmLevel.NONE),
                })},
                {Arrays.asList(new EventDetectorCriteria[] {
                        EventDetectorCriteria.change(DATA_SOURCE_POINT_CRITERIA, AlarmLevel.INFORMATION),
                        EventDetectorCriteria.change(DATA_SOURCE_POINT_CRITERIA, AlarmLevel.CRITICAL)
                })}
        });
    }

    private final DataPointProperties dataPointProperties;
    private static NavigationPage navigationPage;

    public EventDetectorPropertiesServiceTest(List<EventDetectorCriteria> eventDetectorCriterias) {
        dataPointProperties = DataPointProperties.properties(eventDetectorCriterias);
    }

    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private List<EventDetectorObjectsCreator> eventDetectorObjectsCreators = new ArrayList<>();

    @BeforeClass
    public static void setupForAll() {
        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria.criteria(DATA_SOURCE_CRITERIA,
                DATA_POINT_CRITERIA);
        navigationPage = TestWithPageUtil.openNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcePointObjectsCreator.createObjects();
    }

    @AfterClass
    public static void cleanForAll() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Before
    public void setup() {
        for(EventDetectorCriteria eventDetectorCriteria: dataPointProperties.getEventDetectors()) {
            EventDetectorObjectsCreator eventDetectorObjectsCreator =
                    new EventDetectorObjectsCreator(navigationPage, eventDetectorCriteria);
            eventDetectorObjectsCreators.add(eventDetectorObjectsCreator);
            eventDetectorObjectsCreator.createObjects();
        }
    }

    @After
    public void clean() {
        eventDetectorObjectsCreators
                .forEach(EventDetectorObjectsCreator::deleteEventDetectors);
        eventDetectorObjectsCreators.clear();
    }

    @Test
    public void test_get_datapoint_properties() {

        //given:
        PointValueParams pointTarget = new PointValueParams(DATA_POINT_CRITERIA.getXid().getValue());

        //when:
        E2eResponse<DataPointPropertiesResponse> getResponse = TestWithoutPageUtil.getDataPointProperties(pointTarget, a -> {
            DataPointProperties result = new DataPointPropertiesAdapter(a.getDataPoints());
            return dataPointProperties.getEventDetectors().equals(result.getEventDetectors());
        });

        logger.info("response: {}", getResponse);
        DataPointPropertiesResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);

        DataPointProperties result = new DataPointPropertiesAdapter(getResult.getDataPoints());

        assertEquals(dataPointProperties.getEventDetectors(), result.getEventDetectors());

    }
}