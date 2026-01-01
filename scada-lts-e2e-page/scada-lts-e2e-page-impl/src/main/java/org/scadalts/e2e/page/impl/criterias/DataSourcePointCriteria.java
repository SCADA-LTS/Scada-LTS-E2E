package org.scadalts.e2e.page.impl.criterias;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;

import java.util.Objects;

@Getter
@ToString
public class DataSourcePointCriteria<S extends DataSourceCriteria, P extends DataPointCriteria> implements CriteriaObject {

    private final @NonNull S dataSource;
    private final @NonNull P dataPoint;

    public DataSourcePointCriteria(@NonNull S dataSource, @NonNull P dataPoint) {
        this.dataSource = dataSource;
        this.dataPoint = dataPoint;
    }

    @Override
    public final DataSourcePointIdentifier getIdentifier() {
        return new DataSourcePointIdentifier(dataSource.getIdentifier(), dataPoint.getIdentifier());
    }

    public static <S extends DataSourceCriteria, P extends DataPointCriteria> DataSourcePointCriteria<S, P> criteria(S dataSourceCriteria,
                                                                                                                     P dataPointCriteria) {
        return new DataSourcePointCriteria<>(dataSourceCriteria, dataPointCriteria);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSourcePointCriteria<?, ?> that = (DataSourcePointCriteria<?, ?>) o;
        return Objects.equals(dataSource, that.dataSource) && Objects.equals(dataPoint, that.dataPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSource, dataPoint);
    }
}