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
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcesAndPointsPageTestsUtil;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(E2eTestRunner.class)
public class DeleteDataPointPageTest {

    private final String dataPointToDeleteName = "dp_test_to_delete_" + System.nanoTime();

    private DataPointCriteria dataPointToDeleteCriteria;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private DataSourcesAndPointsPageTestsUtil dataSourcesPageTestsUtil;

    @Before
    public void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecound();
        DataPointCriteria dataPointCriteria = DataPointCriteria.binaryAlternate();
        DataPointCriteria dataPointCriteria2 = DataPointCriteria.binaryAlternate();

        dataPointToDeleteCriteria = DataPointCriteria.builder()
                .type(DataPointType.BINARY)
                .identifier(dataPointToDeleteName)
                .changeType(ChangeType.ALTERNATE)
                .startValue("true")
                .build();

        dataSourcesPageTestsUtil = new DataSourcesAndPointsPageTestsUtil(E2eAbstractRunnable.getNavigationPage(), dataSourceCriteria, dataPointCriteria,
                dataPointToDeleteCriteria, dataPointCriteria2);
        editDataSourceWithPointListPageSubject = dataSourcesPageTestsUtil.init();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.clean();
    }

    @Test
    public void test_delete_data_point() {

        //when:
        String bodyBeforeDelete = editDataSourceWithPointListPageSubject.getBodyText();

        //then:
        assertThat(bodyBeforeDelete, containsString(dataPointToDeleteName));

        //and when:
        String bodyAfterDelete = editDataSourceWithPointListPageSubject
                .openDataPointEditor(dataPointToDeleteCriteria)
                .deleteDataPoint()
                .waitOnPage(1000)
                .getBodyText();

        //then:
        assertThat(bodyAfterDelete, not(containsString(dataPointToDeleteName)));
    }
}
