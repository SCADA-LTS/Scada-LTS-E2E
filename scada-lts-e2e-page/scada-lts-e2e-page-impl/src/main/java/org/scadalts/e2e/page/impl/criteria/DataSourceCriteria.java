package org.scadalts.e2e.page.impl.criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;
import org.scadalts.e2e.page.core.util.XpathFactory;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;

@Data
@EqualsAndHashCode
public class DataSourceCriteria implements ObjectCriteria {

    private final String identifier;
    private final DataSourceType type;
    private final UpdatePeriodType updatePeriodType;

    public static DataSourceCriteria virtualDataSourceSecound() {
        String dataSourceName = "ds_test" + System.nanoTime();
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOUND;
        return new DataSourceCriteria(dataSourceName, dataSourceType, updatePeriodType);
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("tr", "row", identifier, type.getName());
    }
}
