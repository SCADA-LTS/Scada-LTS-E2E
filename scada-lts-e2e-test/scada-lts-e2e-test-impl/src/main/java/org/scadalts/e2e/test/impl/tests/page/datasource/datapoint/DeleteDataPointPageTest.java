package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.core.utils.TestObjectsUtilible;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcePointTestObjectsUtil;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(E2eTestRunner.class)
public class DeleteDataPointPageTest {

    private final DataPointIdentifier dataPointToDeleteName = new DataPointIdentifier("dp_test_to_delete_" + System.nanoTime());

    private DataPointCriteria dataPointToDeleteCriteria;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private TestObjectsUtilible<DataSourcesPage, EditDataSourceWithPointListPage> dataSourcesPageTestsUtil;

    @Before
    public void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.binaryAlternate();
        DataPointCriteria dataPointCriteria2 = DataPointCriteria.binaryAlternate();

        dataPointToDeleteCriteria = DataPointCriteria.builder()
                .type(DataPointType.BINARY)
                .identifier(dataPointToDeleteName)
                .changeType(ChangeType.ALTERNATE)
                .startValue("true")
                .build();

        dataSourcesPageTestsUtil = new DataSourcePointTestObjectsUtil(E2eAbstractRunnable.getNavigationPage(), dataSourceCriteria, dataPointCriteria,
                dataPointToDeleteCriteria, dataPointCriteria2);
        editDataSourceWithPointListPageSubject = dataSourcesPageTestsUtil.createObjects();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.deleteObjects();
    }

    @Test
    public void test_delete_data_point() {

        //when:
        String bodyBeforeDelete = editDataSourceWithPointListPageSubject.getBodyText();

        //then:
        assertThat(bodyBeforeDelete, containsString(dataPointToDeleteName.getValue()));

        //and when:
        String bodyAfterDelete = editDataSourceWithPointListPageSubject
                .openDataPointEditor(dataPointToDeleteCriteria)
                .deleteDataPoint()
                .waitOnPage(1000)
                .getBodyText();

        //then:
        assertThat(bodyAfterDelete, not(containsString(dataPointToDeleteName.getValue())));
    }
}
