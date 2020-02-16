package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcesPageTestsUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(E2eTestRunner.class)
public class DeleteDataPointTest {

    private final String dataPointToDeleteName = "dp_test_to_delete_" + System.nanoTime();

    private DataPointCriteria dataPointToDeleteCriteria;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private DataSourcesPageTestsUtil dataSourcesPageTestsUtil;

    @Before
    public void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourcesPageTestsUtil.createDataSourceCriteria();

        DataPointCriteria dataPointCriteria = new DataPointCriteria("dp_test" + System.nanoTime(), DataPointType.BINARY,
                ChangeType.ALTERNATE);
        DataPointCriteria dataPointCriteria2 = new DataPointCriteria("dp_test" + System.nanoTime(), DataPointType.BINARY,
                ChangeType.ALTERNATE);
        dataPointToDeleteCriteria = new DataPointCriteria(dataPointToDeleteName, DataPointType.BINARY,
                ChangeType.ALTERNATE);

        dataSourcesPageTestsUtil = new DataSourcesPageTestsUtil(E2eAbstractRunnable.getNavigationPage(), dataSourceCriteria, dataPointCriteria,
                dataPointToDeleteCriteria, dataPointCriteria2);
        dataSourcesPageTestsUtil.init("true");

        DataSourcesPage dataSourcesPage = dataSourcesPageTestsUtil.openDataSourcesPage();

        editDataSourceWithPointListPageSubject = dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria);
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.clean();
    }

    @Test
    public void test_delete_data_point() {

        //when:
        boolean before = editDataSourceWithPointListPageSubject.containsObject(dataPointToDeleteCriteria);

        //then:
        assertTrue(before);

        //and when:
        boolean result = editDataSourceWithPointListPageSubject
                .openDataPointEditor(dataPointToDeleteCriteria)
                .deleteDataPoint()
                .waitOnPage(1000)
                .containsObject(dataPointToDeleteCriteria);

        //then:
        assertFalse(result);
    }
}
