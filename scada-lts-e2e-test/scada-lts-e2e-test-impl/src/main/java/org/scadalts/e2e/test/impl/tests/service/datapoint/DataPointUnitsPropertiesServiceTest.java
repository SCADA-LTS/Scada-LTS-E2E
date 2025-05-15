package org.scadalts.e2e.test.impl.tests.service.datapoint;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.EngineeringUnit;
import org.scadalts.e2e.page.impl.dicts.EngineeringUnitsType;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(Parameterized.class)
public class DataPointUnitsPropertiesServiceTest {

    @Parameterized.Parameters(name = "number of test: {index}, unit: {0}")
    public static List<EngineeringUnit> data() {
        return EngineeringUnitsType.getUnits();
    }

    private final DataPointProperties dataPointProperties;

    public DataPointUnitsPropertiesServiceTest(EngineeringUnit engineeringUnit) {
        dataPointProperties = DataPointProperties.properties(engineeringUnit);
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

        dataPointObjectsCreator = new VirtualDataPointObjectsCreator(navigationPage, dataSourcePointCriteria);

        DataSourcesPage dataSourcesPage = dataSourcePointObjectsCreator.openPage();
        editDataSourceWithPointListPage = dataSourcesPage.openDataSourceEditor(dataSourcePointCriteria.getDataSource().getIdentifier());

        dataPointCriteria = dataSourcePointCriteria.getDataPoint();
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

        dataPointObjectsCreator.setProperties(dataPointProperties,
                dataPointCriteria.getIdentifier().getType(), dataPointPropertiesPage);

        dataPointPropertiesPage.editDataSource();

    }

    @AfterClass
    public static void cleanForAll() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_get_datapoint_properties() {

        //given:
        PointValueParams pointTarget = new PointValueParams(dataPointCriteria.getXid().getValue());

        //when:
        E2eResponse<DataPointPropertiesResponse> getResponse = TestWithoutPageUtil.getDataPointProperties(pointTarget, a -> {
            DataPointProperties result = new DataPointPropertiesAdapter(a.getDataPoints());
            return dataPointProperties.getEngineeringUnits().equals(result.getEngineeringUnits());
        });

        logger.info("response: {}", getResponse);
        DataPointPropertiesResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);

        DataPointProperties result = new DataPointPropertiesAdapter(getResult.getDataPoints());

        assertEquals(dataPointProperties.getEngineeringUnits(), result.getEngineeringUnits());

    }
}
