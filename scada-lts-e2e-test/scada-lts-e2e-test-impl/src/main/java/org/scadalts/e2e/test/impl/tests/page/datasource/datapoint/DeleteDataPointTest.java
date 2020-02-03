package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteDataPointTest extends DataPointTestsAbstract {

    private final String dataPointToDeleteName = "dp_test_to_delete_" + System.nanoTime();

    private static final DataPointType dataPointType = DataPointType.BINARY;

    private DataPointCriteria dataPointCriteria;
    private DataPointCriteria dataPointToDeleteCriteria;
    private DataPointCriteria dataPointCriteria2;

    @Before
    public void createDataSourceAndPoint() {

        DataSourceTestsUtil.addDataSource(getDataSourceCriteria());

        dataPointCriteria = new DataPointCriteria("dp_test" + System.nanoTime(), dataPointType);
        dataPointToDeleteCriteria = new DataPointCriteria(dataPointToDeleteName, dataPointType);
        dataPointCriteria2 = new DataPointCriteria("dp_test" + System.nanoTime(), dataPointType);

        DataPointCriteria[] criteria = new DataPointCriteria[] {dataPointCriteria,dataPointToDeleteCriteria,dataPointCriteria2};
        DataSourceTestsUtil.addDataPoints(getDataSourceCriteria(), criteria, "true");
    }

    @After
    public void clean() {
        EditDataSourceWithPointListPage pointListPage = DataSourceTestsUtil.openDataSourcesPage()
                .openDataSourceEditor(getDataSourceCriteria());

        boolean exists = pointListPage.openDataPointEditor(dataPointCriteria)
                .deleteDataPoint()
                .openDataPointEditor(dataPointCriteria2)
                .deleteDataPoint()
                .waitOnPage(1000)
                .containsObject(dataPointToDeleteCriteria);

        if(exists)
            pointListPage.openDataPointEditor(dataPointToDeleteCriteria)
                    .deleteDataPoint();

        DataSourceTestsUtil.deteleDataSource(getDataSourceCriteria());
    }

    @Test
    public void test_delete_data_point() {

        //given:
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = DataSourceTestsUtil.openDataSourcesPage()
                .openDataSourceEditor(getDataSourceCriteria());

        //when:
        boolean before = editDataSourceWithPointListPage.containsObject(dataPointToDeleteCriteria);

        //then:
        assertTrue(before);

        //and when:
        boolean result = editDataSourceWithPointListPage
                .openDataPointEditor(dataPointToDeleteCriteria)
                .deleteDataPoint()
                .waitOnPage(1000)
                .containsObject(dataPointToDeleteCriteria);

        //then:
        assertFalse(result);
    }
}
