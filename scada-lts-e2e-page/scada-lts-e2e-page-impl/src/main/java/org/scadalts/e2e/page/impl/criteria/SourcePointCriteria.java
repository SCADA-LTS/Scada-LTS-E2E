package org.scadalts.e2e.page.impl.criteria;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;
import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;
import org.scadalts.e2e.page.core.util.XpathFactory;
import org.scadalts.e2e.page.impl.dict.DataPointType;

@Getter
public class SourcePointCriteria implements ObjectCriteria {

    private final DataSourceCriteria dataSource;
    private final DataPointCriteria dataPoint;

    public SourcePointCriteria(DataSourceCriteria dataSource, DataPointCriteria dataPoint) {
        this.dataSource = dataSource;
        this.dataPoint = dataPoint;
    }

    public static SourcePointCriteria virtualDataSourceBinaryAlternate() {
        return new SourcePointCriteria(DataSourceCriteria.virtualDataSourceSecound(),
                DataPointCriteria.binaryAlternate());
    }

    public static SourcePointCriteria virtualDataSource(DataPointType dataPointType, String dataPointStartValue) {
        return new SourcePointCriteria(DataSourceCriteria.virtualDataSourceSecound(),
                DataPointCriteria.noChange(dataPointType, dataPointStartValue));
    }

    @Override
    public String getIdentifier() {
        return dataSource.getIdentifier() + " - " + dataPoint.getIdentifier();
    }

    @Override
    public E2eDictionary getType() {
        return EmptyType.ANY;
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("tbody", getIdentifier());
    }
}
