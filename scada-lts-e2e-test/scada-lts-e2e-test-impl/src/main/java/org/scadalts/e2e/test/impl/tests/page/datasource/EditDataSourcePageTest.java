package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(TestWithPageRunner.class)
public class EditDataSourcePageTest {

    private DataSourceCriteria criteria;
    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;

    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private DataSourcesPage dataSourcesPage;

    @Before
    public void createDataSource() {
        criteria = DataSourceCriteria.virtualDataSource(UpdatePeriodType.SECOND, 13);
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(), criteria);
        dataSourcesPage = dataSourcePointObjectsCreator.openPage();
        editDataSourceWithPointListPageSubject = dataSourcePointObjectsCreator.createDataSources();
    }

    @After
    public void clean() {
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_edit_update_period_in_data_source() {

        //given:
        int updatePeriodsExp = 12;

        //when
        int updatePeriodsBefore = editDataSourceWithPointListPageSubject
                .getUpdatePeriods();

        //then
        assertNotEquals(updatePeriodsExp, updatePeriodsBefore);

        //and when:
        editDataSourceWithPointListPageSubject
                .setUpdatePeriods(updatePeriodsExp)
                .saveDataSource();

        //then:
        int updatePeriods = dataSourcesPage.reopen()
                .openDataSourceEditor(criteria)
                .getUpdatePeriods();

        assertEquals(updatePeriodsExp, updatePeriods);
    }


    @Test
    public void test_edit_update_period_type_in_data_source() {

        //given:
        UpdatePeriodType updatePeriodsTypeExp = UpdatePeriodType.MILLISECOUND;

        //when
        UpdatePeriodType updatePeriodTypeBefore = editDataSourceWithPointListPageSubject
                .getUpdatePeriodType();

        //then
        assertNotEquals(updatePeriodsTypeExp, updatePeriodTypeBefore);

        //and when:
        editDataSourceWithPointListPageSubject
                .selectUpdatePeriodType(updatePeriodsTypeExp)
                .saveDataSource();

        //then:
        UpdatePeriodType updatePeriodType = dataSourcesPage.reopen()
                .openDataSourceEditor(criteria)
                .getUpdatePeriodType();

        assertEquals(updatePeriodsTypeExp, updatePeriodType);
    }
}
