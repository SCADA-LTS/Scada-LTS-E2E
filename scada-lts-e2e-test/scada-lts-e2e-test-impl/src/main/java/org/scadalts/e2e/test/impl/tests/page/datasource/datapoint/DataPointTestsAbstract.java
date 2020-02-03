package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;

public abstract class DataPointTestsAbstract {

    private DataSourceCriteria dataSourceCriteria;

    public DataPointTestsAbstract() {
        String dataSourceName = "ds_test" + System.nanoTime();
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        dataSourceCriteria = new DataSourceCriteria(dataSourceName, dataSourceType);
    }

    public DataSourceCriteria getDataSourceCriteria() {
        return dataSourceCriteria;
    }
}
