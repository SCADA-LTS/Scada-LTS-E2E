package org.scadalts.e2e.page.impl.criterias;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.DataPointType;


@Getter
@ToString
public class VirtualDataSourcePointCriteria extends DataSourcePointCriteria<UpdateDataSourceCriteria, VirtualDataPointCriteria> {

    public VirtualDataSourcePointCriteria(@NonNull UpdateDataSourceCriteria dataSource, @NonNull VirtualDataPointCriteria dataPoint) {
        super(dataSource, dataPoint);
    }

    public static VirtualDataSourcePointCriteria empty() {
        return new VirtualDataSourcePointCriteria(UpdateDataSourceCriteria.empty(),
                VirtualDataPointCriteria.empty());
    }

    public static VirtualDataSourcePointCriteria virtualDataSourceBinaryAlternate() {
        return new VirtualDataSourcePointCriteria(UpdateDataSourceCriteria.virtualDataSourceSecond(),
                VirtualDataPointCriteria.binaryAlternate());
    }

    public static VirtualDataSourcePointCriteria virtualDataSourceNumericNoChange() {
        return new VirtualDataSourcePointCriteria(UpdateDataSourceCriteria.virtualDataSourceSecond(),
                VirtualDataPointCriteria.numericNoChange());
    }

    public static VirtualDataSourcePointCriteria virtualDataSource(DataPointType dataPointType, String dataPointStartValue) {
        return new VirtualDataSourcePointCriteria(UpdateDataSourceCriteria.virtualDataSourceSecond(),
                VirtualDataPointCriteria.noChange(dataPointType, dataPointStartValue));
    }

    public static VirtualDataSourcePointCriteria virtualDataSource(DataPointType dataPointType, String dataPointStartValue,
                                                            DataPointProperties dataPointProperties) {
        return new VirtualDataSourcePointCriteria(UpdateDataSourceCriteria.virtualDataSourceSecond(),
                VirtualDataPointCriteria.noChange(dataPointType, dataPointStartValue, dataPointProperties));
    }

    public static VirtualDataSourcePointCriteria virtualCriteria(UpdateDataSourceCriteria dataSourceCriteria,
                                                                 VirtualDataPointCriteria dataPointCriteria) {
        return new VirtualDataSourcePointCriteria(dataSourceCriteria, dataPointCriteria);
    }

    public static VirtualDataSourcePointCriteria virtualCriteria(DataSourceIdentifier dataSourceIdentifier, DataPointIdentifier dataPointIdentifier) {
        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria
                .criteriaSecond(dataSourceIdentifier);
        VirtualDataPointCriteria dataPointCriteria = VirtualDataPointCriteria
                .noChange(dataPointIdentifier);
        return new VirtualDataSourcePointCriteria(dataSourceCriteria, dataPointCriteria);
    }

    public static VirtualDataSourcePointCriteria virtualCriteria(UpdateDataSourceCriteria dataSourceCriteria, DataPointIdentifier dataPointIdentifier) {
        VirtualDataPointCriteria dataPointCriteria = VirtualDataPointCriteria
                .noChange(dataPointIdentifier);
        return new VirtualDataSourcePointCriteria(dataSourceCriteria, dataPointCriteria);
    }

    public DataSourcePointCriteria<DataSourceCriteria, DataPointCriteria> to() {
        return new DataSourcePointCriteria<>(getDataSource(), getDataPoint());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
