package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class DeleteDataSourceTest {

    private String dataSourceToDeleteName = "ds_test" + System.nanoTime();
    private DataSourceCriteria dataSourceToDeleteCriteria;

    private final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final DataSourceTestsUtil testsUtil = new DataSourceTestsUtil(navigationPage);
    private DataSourcesPage dataSourcesPageSubject;

    @Before
    public void createDataSource() throws Throwable {
        dataSourceToDeleteCriteria = new DataSourceCriteria(dataSourceToDeleteName, DataSourceType.VIRTUAL_DATA_SOURCE);
        testsUtil.addDataSource(dataSourceToDeleteCriteria);
        dataSourcesPageSubject = testsUtil.openDataSourcesPage();
    }

    @Test
    public void test_delete_data_source() {

        //given
        String body = dataSourcesPageSubject.getBodyText();

        //then:
        assertThat(body, containsString(dataSourceToDeleteName));

        //when:
        body = dataSourcesPageSubject
                .deleteDataSource(dataSourceToDeleteCriteria)
                .reopen()
                .getBodyText();

        //then:
        assertThat(body, not(containsString(dataSourceToDeleteName)));
    }
}
