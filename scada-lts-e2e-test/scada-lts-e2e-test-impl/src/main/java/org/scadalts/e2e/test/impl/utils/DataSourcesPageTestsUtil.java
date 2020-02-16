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

public class DataSourcesPageTestsUtil {

    @Getter
    private DataSourcesPage dataSourcesPage;
    private DataSourceCriteria[] dataSourceCriteria;
    private DataPointCriteria[] dataPointCriteria;

    public DataSourcesPageTestsUtil(NavigationPage navigationPage, DataSourceCriteria dataSourceCriteria, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.dataSourcesPage = navigationPage.openDataSources();
        this.dataSourceCriteria = new DataSourceCriteria[] {dataSourceCriteria};
        this.dataPointCriteria = dataPointCriteria;
    }

    public DataSourcesPageTestsUtil(NavigationPage navigationPage, DataSourceCriteria[] dataSourceCriteria, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.dataSourcesPage = navigationPage.openDataSources();
        this.dataSourceCriteria = dataSourceCriteria;
        this.dataPointCriteria = dataPointCriteria;
    }

    public DataSourcesPageTestsUtil(NavigationPage navigationPage, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.dataSourcesPage = navigationPage.openDataSources();
        this.dataSourceCriteria = new DataSourceCriteria[] {createDataSourceCriteria()};
        this.dataPointCriteria = dataPointCriteria;
    }

    public DataSourcesPageTestsUtil(NavigationPage navigationPage) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.dataSourcesPage = navigationPage.openDataSources();
        this.dataSourceCriteria = new DataSourceCriteria[] {createDataSourceCriteria()};
        this.dataPointCriteria = new DataPointCriteria[] {createDataPointCriteria()};
    }

    public void init(String dataPointStartValue) {
        addDataSources();
        addDataPoints(dataPointStartValue);
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

    public EditDataPointPage addDataPoints(String startValue) {
        EditDataPointPage page = new EditDataPointPage();
        for (DataSourceCriteria dataSourceParam : dataSourceCriteria) {
            for (DataPointCriteria dataPointParam : dataPointCriteria) {
                page = _addDataPoint(dataSourceParam, dataPointParam, startValue);
            }
        }
        return page;
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

    private EditDataPointPage _addDataPoint(DataSourceCriteria sourceParams, DataPointCriteria pointParams, String value) {
        return dataSourcesPage.reopen()
                .openDataSourceEditor(sourceParams)
                .addDataPoint()
                .setDataPointName(pointParams.getIdentifier())
                .selectDataPointType(pointParams.getType())
                .enableSettable()
                .selectChangeType(pointParams.getChangeType())
                .setStartValue(pointParams, value)
                .saveDataPoint()
                .enableDataPoint(pointParams)
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

}
