package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcesAndPointsPageTestsUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class CreateDataSourcePageTest {

    private static final DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
    private static final UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOUND;
    private final String dataSourceName = "ds_test" + System.nanoTime();

    private DataSourcesAndPointsPageTestsUtil dataSourcesPageTestsUtil;
    private DataSourcesPage dataSourcesPageSubject;

    @Before
    public void setup() {
        DataSourceCriteria criteria = new DataSourceCriteria(dataSourceName, dataSourceType, updatePeriodType);
        dataSourcesPageTestsUtil = new DataSourcesAndPointsPageTestsUtil(E2eAbstractRunnable.getNavigationPage(), criteria);
        dataSourcesPageSubject = dataSourcesPageTestsUtil.openDataSourcesPage();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.clean();
    }

    @Test
    public void test_create_data_source() {

        //when:
        dataSourcesPageSubject.openDataSourceCreator(dataSourceType)
                .selectUpdatePeriodType(updatePeriodType)
                .setUpdatePeriods(13)
                .setDataSourceName(dataSourceName)
                .saveDataSource()
                .enableDataSource();

        //and:
        String body = dataSourcesPageSubject.reopen().getBodyText();

        //then
        assertThat(body, containsString(dataSourceName));
    }
}
