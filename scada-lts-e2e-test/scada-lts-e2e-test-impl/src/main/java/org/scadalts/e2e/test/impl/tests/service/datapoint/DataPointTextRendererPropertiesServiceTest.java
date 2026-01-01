package org.scadalts.e2e.test.impl.tests.service.datapoint;

import lombok.extern.log4j.Log4j2;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointTextRendererProperties;
import org.scadalts.e2e.page.impl.dicts.Color;
import org.scadalts.e2e.page.impl.dicts.TextRendererType;
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
public class DataPointTextRendererPropertiesServiceTest {


    @Parameterized.Parameters(name = "number of test: {index}, text renderer: {0}")
    public static DataPointTextRendererProperties[] data() {
        return new DataPointTextRendererProperties[] {
                DataPointTextRendererProperties.builder()
                        .textRendererType(TextRendererType.PLAIN)
                        .suffix("suffix1")
                        .format("")
                        .build(),
                DataPointTextRendererProperties.builder()
                        .textRendererType(TextRendererType.ANALOG)
                        .format("#.#")
                        .suffix("suffix2")
                        .build(),
                DataPointTextRendererProperties.builder()
                        .textRendererType(TextRendererType.TIME)
                        .format("yyyy-MM")
                        .suffix("")
                        .conversionExponent(2)
                        .build(),
                DataPointTextRendererProperties.builder()
                        .textRendererType(TextRendererType.RANGE)
                        .format("yyyy")
                        .suffix("")
                        .rangeValue(RangeValue.builder()
                                .colour(Color.WHITE)
                                .text("text1")
                                .from("1.0")
                                .to("2.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.BLUE)
                                .text("text2")
                                .from("2.01")
                                .to("3.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.MAGENTA)
                                .text("text3")
                                .from("3.01")
                                .to("4.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.BLACK)
                                .text("text4")
                                .from("4.01")
                                .to("5.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.FILLET)
                                .text("text5")
                                .from("5.01")
                                .to("6.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.GRAY)
                                .text("text6")
                                .from("6.01")
                                .to("7.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.GREEN)
                                .text("text7")
                                .from("7.01")
                                .to("8.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.LIGHT_GRAY)
                                .text("text8")
                                .from("8.01")
                                .to("9.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.LIGHT_GREEN)
                                .text("text9")
                                .from("9.01")
                                .to("10.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.NAVY_BLUE)
                                .text("text10")
                                .from("10.01")
                                .to("11.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.YELLOW)
                                .text("text11")
                                .from("11.01")
                                .to("12.0")
                                .build())
                        .rangeValue(RangeValue.builder()
                                .colour(Color.RED)
                                .text("text12")
                                .from("12.01")
                                .to("13.0")
                                .build())
                        .build(),
        };
    }

    private final DataPointProperties dataPointProperties;
    private static NavigationPage navigationPage;

    public DataPointTextRendererPropertiesServiceTest(DataPointTextRendererProperties textRendererProperties) {
        dataPointProperties = DataPointProperties.properties(textRendererProperties);
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
        DataPointPropertiesPage dataPointPropertiesPage;
        try {
            dataPointPropertiesPage = editDataSourceWithPointListPage
                    .openDataPointProperties(dataPointCriteria.getIdentifier());
        } catch (Throwable ex) {
            dataPointPropertiesPage = dataSourcePointObjectsCreator.openPage()
                    .openDataSourceEditor(dataSourcePointCriteria.getDataSource().getIdentifier())
                    .openDataPointProperties(dataPointCriteria.getIdentifier());
        }

        dataPointObjectsCreator.setProperties(dataPointProperties,
                dataPointCriteria.getIdentifier().getType(), dataPointPropertiesPage);

        dataPointPropertiesPage.editDataSource();

    }

    @After
    public void clean() {
        dataPointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_get_datapoint_properties() {

        //given:
        PointValueParams pointTarget = new PointValueParams(dataPointCriteria.getXid().getValue());

        //when:
        E2eResponse<DataPointPropertiesResponse> getResponse = TestWithoutPageUtil.getDataPointProperties(pointTarget, a -> {
            DataPointProperties result = new DataPointPropertiesAdapter(a.getDataPoints());
            return dataPointProperties.getTextRendererProperties().equals(result.getTextRendererProperties());
        });

        logger.info("response: {}", getResponse);
        DataPointPropertiesResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);

        DataPointProperties result = new DataPointPropertiesAdapter(getResult.getDataPoints());

        assertEquals(dataPointProperties.getTextRendererProperties(), result.getTextRendererProperties());

    }
}
