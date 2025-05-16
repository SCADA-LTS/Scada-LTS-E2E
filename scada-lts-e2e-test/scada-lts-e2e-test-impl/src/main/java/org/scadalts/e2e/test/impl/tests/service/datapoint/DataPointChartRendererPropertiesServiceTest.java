package org.scadalts.e2e.test.impl.tests.service.datapoint;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointChartRenderProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.ChartRendererType;
import org.scadalts.e2e.page.impl.dicts.PeriodType;
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
public class DataPointChartRendererPropertiesServiceTest {

    @Parameterized.Parameters(name = "number of test: {index}, chart renderer: {0}")
    public static DataPointChartRenderProperties[] data() {
        return new DataPointChartRenderProperties[] {
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.TABLE)
                        .periodType(PeriodType.NONE)
                        .limit(4)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.IMAGE)
                        .periodType(PeriodType.MINUTES)
                        .period(123)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.IMAGE)
                        .periodType(PeriodType.HOURS)
                        .period(345)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.IMAGE)
                        .periodType(PeriodType.DAYS)
                        .period(678)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.IMAGE)
                        .periodType(PeriodType.WEEKS)
                        .period(910)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.IMAGE)
                        .periodType(PeriodType.MONTHS)
                        .period(1112)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.STATS)
                        .periodType(PeriodType.MINUTES)
                        .period(1314)
                        .includeSum(true)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.STATS)
                        .periodType(PeriodType.HOURS)
                        .period(1415)
                        .includeSum(true)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.STATS)
                        .periodType(PeriodType.DAYS)
                        .period(1516)
                        .includeSum(true)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.STATS)
                        .periodType(PeriodType.WEEKS)
                        .period(1617)
                        .includeSum(true)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.STATS)
                        .periodType(PeriodType.WEEKS)
                        .period(1617)
                        .includeSum(false)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.STATS)
                        .periodType(PeriodType.MONTHS)
                        .period(1718)
                        .includeSum(true)
                        .build(),
                DataPointChartRenderProperties.builder()
                        .chartRendererType(ChartRendererType.NONE)
                        .build(),
        };
    }

    private final DataPointProperties dataPointProperties;

    public DataPointChartRendererPropertiesServiceTest(DataPointChartRenderProperties dataPointChartRenderProperties) {
        dataPointProperties = DataPointProperties.properties(dataPointChartRenderProperties);
    }

    private static DataSourcePointObjectsCreator<UpdateDataSourceCriteria, VirtualDataPointCriteria> dataSourcePointObjectsCreator;
    private static DataPointObjectsCreator<UpdateDataSourceCriteria, VirtualDataPointCriteria> dataPointObjectsCreator;
    private static EditDataSourceWithPointListPage editDataSourceWithPointListPage;
    private static VirtualDataPointCriteria dataPointCriteria;
    private static VirtualDataSourcePointCriteria dataSourcePointCriteria;

    @BeforeClass
    public static void setupForAll() {

        dataSourcePointCriteria = VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange();

        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();
        dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcePointObjectsCreator.createObjects();

        dataPointObjectsCreator = new VirtualDataPointObjectsCreator(navigationPage,dataSourcePointCriteria);

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
        DataPointPropertiesPage dataPointPropertiesPage;
        try {
            dataPointPropertiesPage = editDataSourceWithPointListPage
                    .openDataPointProperties(dataPointCriteria.getIdentifier());
        } catch (Throwable ex) {
            dataPointPropertiesPage = dataSourcePointObjectsCreator.openPage()
                    .openDataSourceEditor(dataSourcePointCriteria.getDataSource().getIdentifier())
                    .openDataPointProperties(dataPointCriteria.getIdentifier());
        }

        dataPointObjectsCreator.reload();
        dataPointObjectsCreator.setProperties(dataPointProperties,
                dataPointCriteria.getIdentifier().getType(), dataPointPropertiesPage);

        dataPointPropertiesPage.editDataSource();

    }

    @Test
    public void test_get_datapoint_properties() {

        //given:
        PointValueParams pointTarget = new PointValueParams(dataPointCriteria.getXid().getValue());

        //when:
        E2eResponse<DataPointPropertiesResponse> getResponse = TestWithoutPageUtil.getDataPointProperties(pointTarget, a -> {
            DataPointProperties result = new DataPointPropertiesAdapter(a.getDataPoints());
            return dataPointProperties.getChartRenderProperties().equals(result.getChartRenderProperties());
        });

        logger.info("response: {}", getResponse);
        DataPointPropertiesResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);

        DataPointProperties result = new DataPointPropertiesAdapter(getResult.getDataPoints());

        assertEquals(dataPointProperties.getChartRenderProperties(), result.getChartRenderProperties());

    }
}
