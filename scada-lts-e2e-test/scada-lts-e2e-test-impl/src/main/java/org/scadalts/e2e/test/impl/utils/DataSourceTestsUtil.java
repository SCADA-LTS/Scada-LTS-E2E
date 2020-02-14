package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;

import java.util.Objects;

import static org.scadalts.e2e.common.utils.ExecutorUtil.execute;

public class DataSourceTestsUtil {

    @Getter
    private DataSourcesPage dataSourcesPage;

    public DataSourceTestsUtil(NavigationPage navigationPage) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException("Not logged in"));
        this.dataSourcesPage = navigationPage.openDataSources();
    }

    public EditDataSourceWithPointListPage addDataSource(DataSourceCriteria params) throws ConfigureTestException {
        return execute(this::_addDataSource, params, ConfigureTestException::new);
    }

    public EditDataPointPage addDataPoint(DataSourceCriteria sourceParams,
                                                 DataPointCriteria pointParams,
                                                 String value) throws ConfigureTestException {
        return execute(this::_addDataPoint, sourceParams, pointParams, value, ConfigureTestException::new);
    }

    public EditDataSourceWithPointListPage addDataPoints(DataSourceCriteria sourceParams,
                                                 DataPointCriteria[] pointParams,
                                                 String value) throws ConfigureTestException {
        return execute(this::_addDataPoints, sourceParams, pointParams, value, ConfigureTestException::new);
    }

    public void deteleDataSource(DataSourceCriteria dataSourceParams) throws ConfigureTestException {
        execute(this::_deteleDataSource, dataSourceParams, ConfigureTestException::new);
    }

    public DataSourcesPage openDataSourcesPage() throws ConfigureTestException {
        return execute(this::_openDataSourcesPage, ConfigureTestException::new);
    }

    public DataSourceCriteria createDataSourceCriteria() {
        String dataSourceName = "ds_test" + System.nanoTime();
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        return new DataSourceCriteria(dataSourceName, dataSourceType);
    }

    private void _deteleDataSource(DataSourceCriteria dataSourceParams) {
        dataSourcesPage.reopen()
                .deleteDataSource(dataSourceParams);
    }

    private DataSourcesPage _openDataSourcesPage() {
        return dataSourcesPage.reopen();
    }

    private EditDataSourceWithPointListPage _addDataPoints(DataSourceCriteria sourceParams, DataPointCriteria[] pointParams, String value) {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage =
                dataSourcesPage.reopen()
                        .openDataSourceEditor(sourceParams);

        for(DataPointCriteria pointParam: pointParams) {
            editDataSourceWithPointListPage.addDataPoint()
                    .setDataPointName(pointParam.getName())
                    .selectDataPointType(pointParam.getType())
                    .enableSettable()
                    .selectChangeType(ChangeType.ALTERNATE)
                    .setStartValue(value)
                    .saveDataPoint()
                    .enableDataPoint(pointParam);
        }

        return editDataSourceWithPointListPage;
    }

    private EditDataPointPage _addDataPoint(DataSourceCriteria sourceParams, DataPointCriteria pointParams, String value) {
        return dataSourcesPage.reopen()
                .openDataSourceEditor(sourceParams)
                .addDataPoint()
                .setDataPointName(pointParams.getName())
                .selectDataPointType(pointParams.getType())
                .enableSettable()
                .selectChangeType(ChangeType.ALTERNATE)
                .setStartValue(value)
                .saveDataPoint()
                .enableDataPoint(pointParams)
                .openDataPointEditor(pointParams);
    }

    private EditDataSourceWithPointListPage _addDataSource(DataSourceCriteria params) {
        return dataSourcesPage.reopen()
                .openDataSourceCreator(params.getType())
                .selectUpdatePeriodType(UpdatePeriodType.SECOUND)
                .setUpdatePeriods(33)
                .setDataSourceName(params.getName())
                .saveDataSource();
    }

}
