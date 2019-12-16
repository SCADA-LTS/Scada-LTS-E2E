package org.scadalts.e2e.tests.page.datasource;

import org.scadalts.e2e.pages.dict.ChangeType;
import org.scadalts.e2e.pages.dict.DataPointType;
import org.scadalts.e2e.pages.dict.DataSourceType;
import org.scadalts.e2e.pages.dict.UpdatePeriodType;
import org.scadalts.e2e.pages.page.datasource.DataSourcesPage;
import org.scadalts.e2e.pages.page.datasource.EditDataSourcePage;
import org.scadalts.e2e.pages.page.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.pages.page.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.tests.E2e;

public abstract class DataSourceTestsAbstract extends E2e {

    private final String dataSourceName = "ds_test" + System.currentTimeMillis();
    private final DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;

    protected EditDataSourceWithPointListPage addDataSource() {
        return addDataSource(getDataSourceName(), getDataSourceType());
    }

    protected EditDataSourceWithPointListPage addDataSource(String dataSourceName, DataSourceType type) {
        DataSourcesPage dataSourcesPage = openDataSourcesPage();
        dataSourcesPage.selectDataSourceType(type);
        EditDataSourcePage edit = dataSourcesPage.openDataSourceCreator();

        edit.selectUpdatePeriodType(UpdatePeriodType.SECOUND);
        edit.setUpdatePeriods(4);
        edit.setDataSourceName(dataSourceName);

        return edit.saveSaveDataSource();
    }

    protected EditDataSourceWithPointListPage addDataPoint(String dataPointName2, DataPointType type, String value) {
        DataSourcesPage dataSourcesPage = openDataSourcesPage();
        EditDataSourceWithPointListPage subjectPage = dataSourcesPage.openDataSourceEditor(getDataSourceName(), getDataSourceType());
        EditDataPointPage pointEditPage = subjectPage.addDataPoint();

        pointEditPage.setDataPointName(dataPointName2);
        pointEditPage.selectDataPointType(type);
        pointEditPage.selectChangeType(ChangeType.ALTERNATE);
        pointEditPage.setStartValue(value);
        pointEditPage.saveDataPoint();

        return subjectPage;
    }

    protected void deteleDataSource() {
        deteleDataSource(getDataSourceName(), getDataSourceType());
    }

    protected void deteleDataSource(String dataSourceName, DataSourceType dataSourceType) {
        DataSourcesPage dataSourcesPage = openDataSourcesPage();
        dataSourcesPage.deleteDataSource(dataSourceName, dataSourceType);
    }

    protected DataSourcesPage openDataSourcesPage() {
        return getHomePage().openDataSources();
    }

    protected String getDataSourceName() {
        return dataSourceName;
    }

    protected DataSourceType getDataSourceType() {
        return dataSourceType;
    }
}
