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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(TestWithPageRunner.class)
public class DeleteDataSourcePageTest {

    private DataSourceIdentifier dataSourceToDeleteName = IdentifierObjectFactory
            .dataSourceDeleteName(DataSourceType.VIRTUAL_DATA_SOURCE);
    private DataSourceCriteria dataSourceToDeleteCriteria;

    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private DataSourcesPage dataSourcesPageSubject;

    @Before
    public void createDataSource() {

        dataSourceToDeleteCriteria = DataSourceCriteria.criteria(dataSourceToDeleteName,
                UpdatePeriodType.SECOND);
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(), dataSourceToDeleteCriteria);
        dataSourcePointObjectsCreator.createDataSources();
        dataSourcesPageSubject = dataSourcePointObjectsCreator.openPage();
    }

    @After
    public void clean() {
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_delete_data_source() {

        //given
        String bodyBeforeDelete = dataSourcesPageSubject.getBodyText();

        //then:
        assertThat(bodyBeforeDelete, containsString(dataSourceToDeleteName.getValue()));

        //when:
        String bodyAfterDelete = dataSourcesPageSubject
                .deleteDataSource(dataSourceToDeleteCriteria.getIdentifier())
                .getBodyText();

        //then:
        assertThat(bodyAfterDelete, not(containsString(dataSourceToDeleteName.getValue())));
    }
}
