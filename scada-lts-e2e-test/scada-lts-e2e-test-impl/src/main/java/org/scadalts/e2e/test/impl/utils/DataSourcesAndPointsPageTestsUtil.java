package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;

import java.util.Objects;

public class DataSourcesAndPointsPageTestsUtil {

    @Getter
    private DataSourcesPage dataSourcesPage;
    private DataSourceCriteria[] dataSourceCriteria;
    private DataPointCriteria[] dataPointCriteria;

    public DataSourcesAndPointsPageTestsUtil(NavigationPage navigationPage, DataSourceCriteria dataSourceCriteria, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.dataSourcesPage = navigationPage.openDataSources();
        this.dataSourceCriteria = new DataSourceCriteria[] {dataSourceCriteria};
        this.dataPointCriteria = dataPointCriteria;
    }

    public DataSourcesAndPointsPageTestsUtil(NavigationPage navigationPage, DataSourceCriteria[] dataSourceCriteria, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.dataSourcesPage = navigationPage.openDataSources();
        this.dataSourceCriteria = dataSourceCriteria;
        this.dataPointCriteria = dataPointCriteria;
    }

    public DataSourcesAndPointsPageTestsUtil(NavigationPage navigationPage, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.dataSourcesPage = navigationPage.openDataSources();
        this.dataSourceCriteria = new DataSourceCriteria[] {createDataSourceCriteria()};
        this.dataPointCriteria = dataPointCriteria;
    }

    public DataSourcesAndPointsPageTestsUtil(NavigationPage navigationPage) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.dataSourcesPage = navigationPage.openDataSources();
        this.dataSourceCriteria = new DataSourceCriteria[] {createDataSourceCriteria()};
        this.dataPointCriteria = new DataPointCriteria[] {createDataPointCriteria()};
    }

    public EditDataSourceWithPointListPage init(String dataPointStartValue) {
        return _addDataSourcesAndPoints(dataPointStartValue);
    }

    public void clean() {
        deleteDataPoints();
        deleteDataSources();
    }

    public EditDataSourceWithPointListPage addDataSources() {
        EditDataSourceWithPointListPage page = new EditDataSourceWithPointListPage();
        for (DataSourceCriteria criteria : dataSourceCriteria) {
            page = _addDataSource(criteria);
        }
        return page;
    }

    public EditDataSourceWithPointListPage addDataPoints(String startValue) {
        EditDataPointPage page = new EditDataPointPage();
        for (DataSourceCriteria dataSourceParam : dataSourceCriteria) {
            EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.reopen()
                    .openDataSourceEditor(dataSourceParam);
            for (DataPointCriteria dataPointParam : dataPointCriteria) {
                page = _addDataPoint(editDataSourceWithPointListPage, dataPointParam, startValue);
            }
        }
        return page.enableAllDataPoint();
    }

    public static DataSourceCriteria createDataSourceCriteria() {
        String dataSourceName = "ds_test" + System.nanoTime();
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOUND;
        return new DataSourceCriteria(dataSourceName, dataSourceType, updatePeriodType);
    }

    public static DataPointCriteria createDataPointCriteria() {
        String dataPointName = "dp_test" + System.nanoTime();
        DataPointType dataPointType = DataPointType.BINARY;
        ChangeType changeType = ChangeType.ALTERNATE;
        return new DataPointCriteria(dataPointName, dataPointType, changeType);
    }

    public void deleteDataSources() {
        DataSourcesPage page = dataSourcesPage.reopen();
        for (DataSourceCriteria dataSourceParam : dataSourceCriteria) {
            if(page.containsObject(dataSourceParam))
                page.deleteDataSource(dataSourceParam);
        }
    }

    public void deleteDataPoints() {
        DataSourcesPage page = dataSourcesPage.reopen();
        for (DataSourceCriteria dataSourceParam : dataSourceCriteria) {
            if(page.containsObject(dataSourceParam)) {
                EditDataSourceWithPointListPage editDataSourcePage = page
                        .openDataSourceEditor(dataSourceParam);
                for (DataPointCriteria dataPointParam : dataPointCriteria) {
                    if (editDataSourcePage.containsObject(dataPointParam))
                        editDataSourcePage.openDataPointEditor(dataPointParam)
                                .deleteDataPoint();
                }
            }
        }
    }

    public DataSourcesPage openDataSourcesPage() {
        return dataSourcesPage.reopen();
    }

    private EditDataPointPage _addDataPoint(EditDataSourceWithPointListPage page, DataPointCriteria pointParams, String startValue) {
        return page.addDataPoint()
                .setDataPointName(pointParams.getIdentifier())
                .selectDataPointType(pointParams.getType())
                .enableSettable()
                .selectChangeType(pointParams.getChangeType())
                .setStartValue(pointParams, startValue)
                .saveDataPoint()
                .openDataPointEditor(pointParams);
    }

    private EditDataSourceWithPointListPage _addDataSource(DataSourceCriteria params) {
        return dataSourcesPage.reopen()
                .openDataSourceCreator(params.getType())
                .selectUpdatePeriodType(params.getUpdatePeriodType())
                .setUpdatePeriods(33)
                .setDataSourceName(params.getIdentifier())
                .saveDataSource()
                .enableDataSource();
    }

    private EditDataSourceWithPointListPage _addDataSourcesAndPoints(String startValue) {
        EditDataPointPage editDataPointPage = new EditDataPointPage();
        for (DataSourceCriteria dataSourceParams : dataSourceCriteria) {
            EditDataSourceWithPointListPage editDataSourceWithPointListPage = _addDataSource(dataSourceParams);
            for (DataPointCriteria pointParams : dataPointCriteria) {
                editDataPointPage = _addDataPoint(editDataSourceWithPointListPage, pointParams, startValue);
            }

        }
        return editDataPointPage.enableAllDataPoint();
    }
}
