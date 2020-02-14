package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(E2eTestRunner.class)
public class DeleteDataPointTest {

    private final String dataPointToDeleteName = "dp_test_to_delete_" + System.nanoTime();

    private static final DataPointType dataPointType = DataPointType.BINARY;

    private DataPointCriteria dataPointCriteria;
    private DataPointCriteria dataPointToDeleteCriteria;
    private DataPointCriteria dataPointCriteria2;

    private final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final DataSourceTestsUtil testsUtil = new DataSourceTestsUtil(navigationPage);
    private DataSourcesPage dataSourcesPage;
    private DataSourceCriteria dataSourceCriteria;

    @Before
    public void createDataSourceAndPoint() throws ConfigureTestException {

        dataSourceCriteria = testsUtil.createDataSourceCriteria();

        testsUtil.addDataSource(dataSourceCriteria);

        dataPointCriteria = new DataPointCriteria("dp_test" + System.nanoTime(), dataPointType);
        dataPointToDeleteCriteria = new DataPointCriteria(dataPointToDeleteName, dataPointType);
        dataPointCriteria2 = new DataPointCriteria("dp_test" + System.nanoTime(), dataPointType);

        DataPointCriteria[] criteria = new DataPointCriteria[] {dataPointCriteria,dataPointToDeleteCriteria,dataPointCriteria2};
        testsUtil.addDataPoints(dataSourceCriteria, criteria, "true");

        dataSourcesPage = testsUtil.openDataSourcesPage();
    }

    @After
    public void clean() throws ConfigureTestException {
        EditDataSourceWithPointListPage pointListPage = testsUtil.openDataSourcesPage()
                .openDataSourceEditor(dataSourceCriteria);

        boolean exists = pointListPage.openDataPointEditor(dataPointCriteria)
                .deleteDataPoint()
                .openDataPointEditor(dataPointCriteria2)
                .deleteDataPoint()
                .waitOnPage(1000)
                .containsObject(dataPointToDeleteCriteria);

        if(exists)
            pointListPage.openDataPointEditor(dataPointToDeleteCriteria)
                    .deleteDataPoint();

        testsUtil.deteleDataSource(dataSourceCriteria);
    }

    @Test
    public void test_delete_data_point() {

        //given:
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria);

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
