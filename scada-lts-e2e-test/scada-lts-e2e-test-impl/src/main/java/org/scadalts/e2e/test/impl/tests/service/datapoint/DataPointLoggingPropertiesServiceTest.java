package org.scadalts.e2e.test.impl.tests.service.datapoint;

import lombok.extern.log4j.Log4j2;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.*;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.datapoint.DataPointPropertiesResponse;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.test.impl.creators.DataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.DataPointPropertiesAdapter;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(Parameterized.class)
public class DataPointLoggingPropertiesServiceTest {


    @Parameterized.Parameters(name = "number of test: {index}, logging: {0}")
    public static DataPointLoggingProperties[] data() {
        return new DataPointLoggingProperties[] {
                DataPointLoggingProperties.builder()
                        .loggingType(LoggingType.INTERVAL)
                        .purgeType(PurgeType.DAYS)
                        .purgePeriod(123)
                        .intervalLoggingType(IntervalLoggingType.INSTANT)
                        .intervalLoggingPeriodType(IntervalLoggingPeriodType.MINUTES)
                        .intervalLoggingPeriod(56)
                        .discardExtremeValues(true)
                        .discardHighLimit("2.0")
                        .discardLowLimit("1.0")
                        .defaultCacheSize(34)
                        .build(),
                DataPointLoggingProperties.builder()
                        .loggingType(LoggingType.INTERVAL)
                        .purgeType(PurgeType.DAYS)
                        .purgePeriod(67)
                        .intervalLoggingType(IntervalLoggingType.MINIMUM)
                        .intervalLoggingPeriodType(IntervalLoggingPeriodType.MINUTES)
                        .intervalLoggingPeriod(78)
                        .discardExtremeValues(true)
                        .discardHighLimit("3.0")
                        .discardLowLimit("2.0")
                        .defaultCacheSize(23)
                        .build(),
                DataPointLoggingProperties.builder()
                        .loggingType(LoggingType.ON_CHANGE)
                        .purgeType(PurgeType.DAYS)
                        .purgePeriod(78)
                        .discardExtremeValues(true)
                        .discardHighLimit("1.0")
                        .discardLowLimit("0.0")
                        .defaultCacheSize(910)
                        .tolerance("0.4")
                        .build(),
                DataPointLoggingProperties.builder()
                        .loggingType(LoggingType.ALL)
                        .purgeType(PurgeType.YEARS)
                        .purgePeriod(1011)
                        .discardExtremeValues(true)
                        .discardHighLimit("1.0")
                        .discardLowLimit("0.0")
                        .defaultCacheSize(1112)
                        .build(),
                DataPointLoggingProperties.builder()
                        .loggingType(LoggingType.NONE)
                        .discardExtremeValues(true)
                        .discardHighLimit("1.0")
                        .discardLowLimit("0.0")
                        .defaultCacheSize(1213)
                        .build(),
                DataPointLoggingProperties.builder()
                        .loggingType(LoggingType.NONE)
                        .discardExtremeValues(false)
                        .defaultCacheSize(1314)
                        .build(),
                DataPointLoggingProperties.builder()
                        .loggingType(LoggingType.ALL)
                        .purgeType(PurgeType.MONTHS)
                        .purgePeriod(88)
                        .discardExtremeValues(true)
                        .discardHighLimit("1.0")
                        .discardLowLimit("0.0")
                        .defaultCacheSize(1415)
                        .build()
        };
    }

    private final DataPointProperties dataPointProperties;
    private static NavigationPage navigationPage;

    public DataPointLoggingPropertiesServiceTest(DataPointLoggingProperties loggingProperties) {
        dataPointProperties = DataPointProperties.properties(loggingProperties);
    }

    private static DataSourcePointObjectsCreator<UpdateDataSourceCriteria, VirtualDataPointCriteria> dataSourcePointObjectsCreator;
    private static EditDataSourceWithPointListPage editDataSourceWithPointListPage;
    private static VirtualDataSourcePointCriteria dataSourcePointCriteria;
    private static VirtualDataPointCriteria dataPointCriteria;
    private DataPointObjectsCreator<UpdateDataSourceCriteria, VirtualDataPointCriteria> dataPointObjectsCreator;

    @BeforeClass
    public static void setupForAll() {

        dataSourcePointCriteria = VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange();

        navigationPage = TestWithPageUtil.openNavigationPage();
        dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcePointObjectsCreator.createObjects();

        DataSourcesPage dataSourcesPage = dataSourcePointObjectsCreator.openPage();
        editDataSourceWithPointListPage = dataSourcesPage.openDataSourceEditor(dataSourcePointCriteria.getDataSource().getIdentifier());

        dataPointCriteria = dataSourcePointCriteria.getDataPoint();
    }

    @AfterClass
    public static void cleanForAll() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Before
    public void setup() {

        dataPointObjectsCreator = new VirtualDataPointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataPointObjectsCreator.createObjects();
        DataPointPropertiesPage dataPointPropertiesPage = editDataSourceWithPointListPage
                .openDataPointProperties(dataPointCriteria.getIdentifier());

        dataPointObjectsCreator.setProperties(dataPointProperties,
                dataPointCriteria.getIdentifier().getType(), dataPointPropertiesPage);

    }

    @After
    public void clean() {
        if(dataPointObjectsCreator != null)
            dataPointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_get_datapoint_properties() {

        //given:
        PointValueParams pointTarget = new PointValueParams(dataPointCriteria.getXid().getValue());

        //when:
        E2eResponse<DataPointPropertiesResponse> getResponse = TestWithoutPageUtil.getDataPointProperties(pointTarget, a -> {
            DataPointProperties result = new DataPointPropertiesAdapter(a.getDataPoints());
            return dataPointProperties.getLoggingProperties().equals(result.getLoggingProperties());
        });

        logger.info("response: {}", getResponse);
        DataPointPropertiesResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);

        DataPointProperties result = new DataPointPropertiesAdapter(getResult.getDataPoints());

        assertEquals(dataPointProperties.getLoggingProperties(), result.getLoggingProperties());

    }
}
