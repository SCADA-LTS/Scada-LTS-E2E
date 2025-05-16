package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestDataBatch;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@Log4j2
@RunWith(Parameterized.class)
public class GetLivesNumberActivateInactivateFiveSizeSeqServiceTest {

    @Parameterized.Parameters(name = "{index}: sequence: {0}")
    public static List<TestDataBatch> data() {
        List<TestDataBatch> result = new ArrayList<>();

        result.addAll(generateDataTest(4, DataPointNotifierType.ALARM, LoggingType.ON_CHANGE, 0));
        result.addAll(generateDataTest(4, DataPointNotifierType.ALARM, LoggingType.ON_CHANGE, 1));
        result.addAll(generateDataTest(4, DataPointNotifierType.STORUNG, LoggingType.ON_CHANGE, 0));
        result.addAll(generateDataTest(4, DataPointNotifierType.STORUNG, LoggingType.ON_CHANGE, 1));
        return result;
    }

    private final TestDataBatch testDataBatch;

    public GetLivesNumberActivateInactivateFiveSizeSeqServiceTest(TestDataBatch testDataBatch) {
        this.testDataBatch = testDataBatch;
    }

    private PaginationParams paginationParams = PaginationParams.all();


    private static UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond();
    private static VirtualDataSourcePointObjectsCreator dataSourcePointObjectsCreator;

    private StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator;

    private static NavigationPage navigationPage;

    @BeforeClass
    public static void createDataSource() {
        navigationPage = TestWithPageUtil.openNavigationPage();
        dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(navigationPage, dataSourceCriteria);
        dataSourcePointObjectsCreator.createObjects();
    }

    @Before
    public void setup() {
        navigationPage = TestWithPageUtil.openNavigationPage(dataSourcePointObjectsCreator);

        VirtualDataPointCriteria point = VirtualDataPointCriteria.noChange(testDataBatch.getDataPointIdentifier(),
                String.valueOf(testDataBatch.getStartValue()),
                DataPointLoggingProperties.logging(testDataBatch.getLoggingType()));

        storungsAndAlarmsObjectsCreator = new StorungsAndAlarmsObjectsCreator(navigationPage, dataSourceCriteria, point);
        storungsAndAlarmsObjectsCreator.createObjects();

        List<StorungAlarmResponse> storungAlarmRespons =
                getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(),
                a -> a.size() == testDataBatch.getStartAlarmsNumber(),
                paginationParams);
        String msg = MessageFormat.format(AFTER_INITIALIZING_POINT_VALUE_WITH_X_THEN_Y_Z_WAS_GENERATED,
                testDataBatch.getStartValue(), testDataBatch.getStartAlarmsNumber(),
                testDataBatch.getDataPointNotifierType().getName());

        assertEquals(msg, testDataBatch.getStartAlarmsNumber(), storungAlarmRespons.size());
    }

    @After
    public void clean() {
        if(storungsAndAlarmsObjectsCreator != null)
            storungsAndAlarmsObjectsCreator.deleteAlaramsAndDataPoints();
    }

    @AfterClass
    public static void cleanAll() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_when_set_sequence_then_x_size_lives() {

        //when:
        storungsAndAlarmsObjectsCreator.setDataPointValues(testDataBatch.getSequencePointValue());

        //and when:
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(),
                a -> getActiveAlarmsFromResponseNumber(a) == testDataBatch.getActiveAlarmsNumber()
                && getInactiveAlarmsFromResponseNumber(a) == testDataBatch.getInactiveAlarmsNumber(),
                paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_ACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getActiveAlarmsNumber());
        assertEquals(msg, testDataBatch.getActiveAlarmsNumber(), getActiveAlarmsFromResponseNumber(storungAlarmRespons));

        //and then:
        msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getInactiveAlarmsNumber());
        assertEquals(msg, testDataBatch.getInactiveAlarmsNumber(), getInactiveAlarmsFromResponseNumber(storungAlarmRespons));

    }
}
