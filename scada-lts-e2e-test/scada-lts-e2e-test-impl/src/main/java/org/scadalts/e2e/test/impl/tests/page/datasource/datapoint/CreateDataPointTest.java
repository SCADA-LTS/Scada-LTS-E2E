package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;

import static org.junit.Assert.assertTrue;

@RunWith(E2eTestRunner.class)
public class CreateDataPointTest {

    private static final DataPointType dataPointType = DataPointType.BINARY;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private final String dataPointToCreateName = "dp_test" + System.nanoTime();
    private DataPointCriteria dataPointCreatedCriteria = new DataPointCriteria(dataPointToCreateName, dataPointType);

    private final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final DataSourceTestsUtil testsUtil = new DataSourceTestsUtil(navigationPage);

    private DataSourceCriteria dataSourceCriteria;

    @Before
    public void createDataSource() throws ConfigureTestException {
        dataSourceCriteria = testsUtil.createDataSourceCriteria();
        editDataSourceWithPointListPageSubject = testsUtil.addDataSource(dataSourceCriteria);
        editDataSourceWithPointListPageSubject.dataSourceOnOff();
    }

    @After
    public void clean() throws ConfigureTestException {
        testsUtil.openDataSourcesPage()
                .openDataSourceEditor(dataSourceCriteria)
                .openDataPointEditor(dataPointCreatedCriteria)
                .deleteDataPoint();
        testsUtil.deteleDataSource(dataSourceCriteria);
    }

    @Test
    public void test_create_data_point() throws ConfigureTestException {

        //when:
        editDataSourceWithPointListPageSubject.addDataPoint()
                .setDataPointName(dataPointToCreateName)
                .enableSettable()
                .selectDataPointType(dataPointType)
                .selectChangeType(ChangeType.ALTERNATE)
                .setStartValue("true")
                .saveDataPoint()
                .enableDataPoint(dataPointCreatedCriteria);

        //then:
        boolean result = testsUtil.openDataSourcesPage()
                .openDataSourceEditor(dataSourceCriteria)
                .containsObject(dataPointCreatedCriteria);

        assertTrue(result);
    }


}