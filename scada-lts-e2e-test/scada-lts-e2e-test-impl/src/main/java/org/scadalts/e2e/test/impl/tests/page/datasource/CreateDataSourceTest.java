package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class CreateDataSourceTest {

    private final String dataSourceName = "ds_test" + System.nanoTime();
    private final DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
    private final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final DataSourceTestsUtil testsUtil = new DataSourceTestsUtil(navigationPage);
    private DataSourcesPage dataSourcesPageSubject;

    @Before
    public void setup() throws ConfigureTestException {
        dataSourcesPageSubject = testsUtil.openDataSourcesPage();
    }

    @After
    public void clean() throws Throwable {
        DataSourceCriteria criteria = new DataSourceCriteria(dataSourceName, dataSourceType);
        testsUtil.deteleDataSource(criteria);
    }

    @Test
    public void test_create_data_source() {

        //when:
        dataSourcesPageSubject.openDataSourceCreator(dataSourceType)
                .selectUpdatePeriodType(UpdatePeriodType.SECOUND)
                .setUpdatePeriods(13)
                .setDataSourceName(dataSourceName)
                .saveDataSource()
                .dataSourceOnOff();

        //then:
        String body = dataSourcesPageSubject.reopen().getBodyText();

        assertThat(body, containsString(dataSourceName));
    }
}
