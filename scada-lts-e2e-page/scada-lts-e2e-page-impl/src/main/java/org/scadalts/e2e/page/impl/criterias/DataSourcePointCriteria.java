package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;

import java.util.Objects;

@Data
@ToString
@EqualsAndHashCode
public class DataSourcePointCriteria implements CriteriaObject {

    private final @NonNull DataSourceCriteria dataSource;
    private final @NonNull DataPointCriteria dataPoint;

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
    public DataSourcePointIdentifier getIdentifier() {
        return new DataSourcePointIdentifier(dataSource.getIdentifier().getValue() + " - " + dataPoint.getIdentifier().getValue());
    }

    @Override
    public DictionaryObject getType() {
        return DictionaryObject.ANY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSourcePointCriteria)) return false;
        DataSourcePointCriteria that = (DataSourcePointCriteria) o;
        return Objects.equals(getDataSource(), that.getDataSource()) &&
                Objects.equals(getDataPoint(), that.getDataPoint());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDataSource(), getDataPoint());
    }
}
