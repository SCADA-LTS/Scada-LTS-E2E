package org.scadalts.e2e.page.impl.criterias;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.common.dicts.E2eDictionary;
import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.IdentifierObject;
import org.scadalts.e2e.page.core.utils.XpathFactory;
import org.scadalts.e2e.page.impl.dicts.DataPointType;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class DataSourcePointCriteria implements CriteriaObject {

    private final DataSourceCriteria dataSource;
    private final DataPointCriteria dataPoint;

    private DataSourcePointCriteria(DataSourceCriteria dataSource, DataPointCriteria dataPoint) {
        this.dataSource = dataSource;
        this.dataPoint = dataPoint;
    }

    public static DataSourcePointCriteria virtualDataSourceBinaryAlternate() {
        return new DataSourcePointCriteria(DataSourceCriteria.virtualDataSourceSecond(),
                DataPointCriteria.binaryAlternate());
    }

    public static DataSourcePointCriteria virtualDataSource(DataPointType dataPointType, String dataPointStartValue) {
        return new DataSourcePointCriteria(DataSourceCriteria.virtualDataSourceSecond(),
                DataPointCriteria.noChange(dataPointType, dataPointStartValue));
    }

    public static DataSourcePointCriteria criteria(DataSourceCriteria dataSourceCriteria,
                                                   DataPointCriteria dataPointCriteria) {
        return new DataSourcePointCriteria(dataSourceCriteria, dataPointCriteria);
    }

    public static DataSourcePointCriteria criteria(DataSourceIdentifier dataSourceIdentifier, DataPointIdentifier dataPointIdentifier) {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria
                .virtualDataSourceSecond(dataSourceIdentifier);
        DataPointCriteria dataPointCriteria = DataPointCriteria
                .numericNoChange(dataPointIdentifier);
        return new DataSourcePointCriteria(dataSourceCriteria, dataPointCriteria);
    }

    public static DataSourcePointCriteria criteria(DataSourceCriteria dataSourceCriteria, DataPointIdentifier dataPointIdentifier) {
        DataPointCriteria dataPointCriteria = DataPointCriteria
                .numericNoChange(dataPointIdentifier);
        return new DataSourcePointCriteria(dataSourceCriteria, dataPointCriteria);
    }

    @Override
    public IdentifierObject getIdentifier() {
        return new DataSourcePointIdentifier(dataSource.getIdentifier().getValue() + " - " + dataPoint.getIdentifier().getValue());
    }

    @Override
    public E2eDictionary getType() {
        return EmptyType.ANY;
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("tbody", getIdentifier().getValue());
    }
}
