package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(TestWithPageRunner.class)
public class CreateDataSourcePageTest {

    private static final DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
    private static final UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOND;
    private final DataSourceIdentifier dataSourceName = IdentifierObjectFactory.dataSourceName();

    private DataSourcePointObjectsCreator dataSourcesPageTestsUtil;
    private DataSourcesPage dataSourcesPageSubject;

    @Before
    public void setup() {
        DataSourceCriteria criteria = DataSourceCriteria.criteria(dataSourceName,updatePeriodType,dataSourceType);
        dataSourcesPageTestsUtil = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(), criteria);
        dataSourcesPageSubject = dataSourcesPageTestsUtil.openPage();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.deleteObjects();
    }

    @Test
    public void test_create_data_source() {

        //when:
        dataSourcesPageSubject.openDataSourceCreator(dataSourceType)
                .selectUpdatePeriodType(updatePeriodType)
                .setUpdatePeriods(13)
                .setDataSourceName(dataSourceName)
                .saveDataSource()
                .enableDataSource(true);

        //and:
        String body = dataSourcesPageSubject.reopen().getBodyText();

        //then
        assertThat(body, containsString(dataSourceName.getValue()));
    }
}
