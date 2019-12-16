package org.scadalts.e2e.tests.page.datasource;

import org.junit.*;
import org.scadalts.e2e.pages.dict.DataSourceType;
import org.scadalts.e2e.pages.dict.UpdatePeriodType;
import org.scadalts.e2e.pages.page.datasource.DataSourcesPage;
import org.scadalts.e2e.pages.page.datasource.EditDataSourceWithPointListPage;

import static org.junit.Assert.assertEquals;

public class EditDataSourceTest extends DataSourceTestsAbstract {

    private DataSourcesPage subjectPage = openDataSourcesPage();

    private static final String dataSourceName = "ds_test" + System.currentTimeMillis();
    private static final DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;

    @Before
    public void createDataSource() {
        addDataSource(dataSourceName, dataSourceType);
        subjectPage = openDataSourcesPage();
    }

    @After
    public void clean() {
        deteleDataSource(dataSourceName, dataSourceType);
    }

    @Test
    public void test_edit_update_period_in_data_source() {

        //given:
        EditDataSourceWithPointListPage edit = subjectPage.openDataSourceEditor(dataSourceName, dataSourceType);
        int updatePeriodsExp = 12;

        //when:
        edit.setUpdatePeriods(updatePeriodsExp);
        edit.saveSaveDataSource();

        //and:
        subjectPage = openDataSourcesPage();
        edit = subjectPage.openDataSourceEditor(dataSourceName, dataSourceType);

        //and:
        int updatePeriods = edit.getUpdatePeriods();

        //then:
        assertEquals(updatePeriodsExp, updatePeriods);
    }


    @Test
    public void test_edit_update_period_type_in_data_source() {

        //given:
        EditDataSourceWithPointListPage edit = subjectPage.openDataSourceEditor(dataSourceName, dataSourceType);
        UpdatePeriodType updatePeriodsTypeExp = UpdatePeriodType.MILLISECOUND;

        //when:
        edit.selectUpdatePeriodType(updatePeriodsTypeExp);
        edit.saveSaveDataSource();

        //and:
        subjectPage = getHomePage().openDataSources();
        edit = subjectPage.openDataSourceEditor(dataSourceName, dataSourceType);

        //and:
        UpdatePeriodType updatePeriods = edit.getUpdatePeriodType();

        //then:
        assertEquals(updatePeriodsTypeExp, updatePeriods);
    }
}
