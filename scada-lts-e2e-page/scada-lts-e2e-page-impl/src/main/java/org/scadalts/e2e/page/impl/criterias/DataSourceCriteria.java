package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;

import javax.validation.constraints.Min;
import java.util.Objects;

@Data
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class DataSourceCriteria implements CriteriaObject, GetXid {

    private final @NonNull Xid xid;
    private final @NonNull DataSourceIdentifier identifier;
    private final @NonNull UpdatePeriodType updatePeriodType;
    private final @Min(1) int updatePeriodValue;
    private final boolean enabled;

    public static DataSourceCriteria virtualDataSourceSecond() {
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOND;
        Xid xid = Xid.xidForDataSource();
        int updatePeriodValue = 2;
        return DataSourceCriteria.builder()
                .identifier(IdentifierObjectFactory.dataSourceName(dataSourceType))
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .enabled(true)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria criteriaSecond(DataSourceIdentifier identifier) {
        UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOND;
        Xid xid = Xid.xidForDataSource();
        int updatePeriodValue = 2;
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .enabled(true)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria criteria(DataSourceIdentifier identifier, UpdatePeriodType updatePeriodType,
                                              int updatePeriodValue) {
        Xid xid = Xid.xidForDataSource();
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .enabled(true)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria virtualDataSource(UpdatePeriodType updatePeriodType, int updatePeriodValue) {
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        Xid xid = Xid.xidForDataSource();
        return DataSourceCriteria.builder()
                .identifier(IdentifierObjectFactory.dataSourceName(dataSourceType))
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .enabled(true)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria criteria(DataSourceIdentifier identifier, UpdatePeriodType updatePeriodType) {
        Xid xid = Xid.xidForDataSource();
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .enabled(true)
                .updatePeriodValue(2)
                .build();
    }

    public static DataSourceCriteria criteriaSecond(DataSourceIdentifier identifier, boolean enabled) {
        Xid xid = Xid.xidForDataSource();
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(UpdatePeriodType.SECOND)
                .xid(xid)
                .enabled(enabled)
                .updatePeriodValue(2)
                .build();
    }

    public static DataSourceCriteria criteriaPeriodTypeAny(DataSourceIdentifier identifier, boolean enabled) {
        Xid xid = Xid.xidForDataSource();
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(UpdatePeriodType.ANY)
                .xid(xid)
                .enabled(enabled)
                .updatePeriodValue(2)
                .build();
    }

    public static DataSourceCriteria virtualDataSourceSecond(Xid xid) {
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOND;
        int updatePeriodValue = 2;
        return DataSourceCriteria.builder()
                .identifier(IdentifierObjectFactory.dataSourceName(dataSourceType))
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .enabled(true)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSourceCriteria)) return false;
        DataSourceCriteria that = (DataSourceCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
