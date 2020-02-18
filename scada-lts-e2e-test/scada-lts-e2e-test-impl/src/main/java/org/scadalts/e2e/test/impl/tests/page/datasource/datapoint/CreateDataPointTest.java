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
import org.scadalts.e2e.test.impl.utils.DataSourcesAndPointsPageTestsUtil;

import static org.junit.Assert.assertTrue;

@RunWith(E2eTestRunner.class)
public class CreateDataPointTest {

    private static final DataPointType dataPointType = DataPointType.BINARY;
    private static final ChangeType changeType = ChangeType.ALTERNATE;
    private final String dataPointToCreateName = "dp_test" + System.nanoTime();

    private DataPointCriteria dataPointCreatedCriteria;
    private DataSourceCriteria dataSourceCriteria;

    private DataSourcesPage dataSourcesPage;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;

    private DataSourcesAndPointsPageTestsUtil dataSourcesPageTestsUtil;

    @Before
    public void createDataSource() {
        dataSourceCriteria = DataSourcesAndPointsPageTestsUtil.createDataSourceCriteria();
        dataPointCreatedCriteria = new DataPointCriteria(dataPointToCreateName, dataPointType, changeType);
        dataSourcesPageTestsUtil = new DataSourcesAndPointsPageTestsUtil(E2eAbstractRunnable.getNavigationPage(), dataSourceCriteria, dataPointCreatedCriteria);

        dataSourcesPage = dataSourcesPageTestsUtil.openDataSourcesPage();
        editDataSourceWithPointListPageSubject = dataSourcesPageTestsUtil.addDataSources();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.clean();
    }

    @Test
    public void test_create_data_point() {

        //when:
        editDataSourceWithPointListPageSubject.addDataPoint()
                .setDataPointName(dataPointToCreateName)
                .enableSettable()
                .selectDataPointType(dataPointType)
                .selectChangeType(changeType)
                .setStartValue(dataPointCreatedCriteria,"true")
                .saveDataPoint()
                .enableDataPoint(dataPointCreatedCriteria);

        //then:
        boolean result = dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria)
                .containsObject(dataPointCreatedCriteria);

        assertTrue(result);
    }


}