package org.scadalts.e2e.page.impl.criterias;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;

import javax.validation.constraints.Min;
import java.util.Objects;

import static java.text.MessageFormat.format;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class DataSourceCriteria implements CriteriaObject, GetXid {

    private final @NonNull Xid xid;
    private final @NonNull DataSourceIdentifier identifier;
    private final @NonNull UpdatePeriodType updatePeriodType;
    private final @Min(1) int updatePeriodValue;
    private final boolean enabled;

    private static final UpdatePeriodType UPDATE_PERIOD_TYPE_DEFAULT = UpdatePeriodType.SECOND;
    private static final DataSourceType DATA_SOURCE_TYPE_DEFAULT = DataSourceType.VIRTUAL_DATA_SOURCE;
    private static final int UPDATE_PERIOD_VALUE_DEFAULT = 1;

    public static DataSourceCriteria empty() {
        return DataSourceCriteria.builder()
                .identifier(new DataSourceIdentifier("", DataSourceType.NONE))
                .updatePeriodType(UPDATE_PERIOD_TYPE_DEFAULT)
                .xid(new Xid(""))
                .enabled(true)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static DataSourceCriteria virtualDataSourceSecond() {
        return DataSourceCriteria.builder()
                .identifier(IdentifierObjectFactory.dataSourceName(DATA_SOURCE_TYPE_DEFAULT))
                .updatePeriodType(UPDATE_PERIOD_TYPE_DEFAULT)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static DataSourceCriteria criteriaSecond(DataSourceIdentifier identifier) {
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(UPDATE_PERIOD_TYPE_DEFAULT)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static DataSourceCriteria criteria(DataSourceIdentifier identifier, UpdatePeriodType updatePeriodType,
                                              int updatePeriodValue) {
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(updatePeriodType)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria criteria(DataSourceIdentifier identifier, UpdatePeriodType updatePeriodType,
                                              int updatePeriodValue, boolean enabled) {
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(updatePeriodType)
                .xid(Xid.dataSource())
                .enabled(enabled)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria virtualDataSource(UpdatePeriodType updatePeriodType, int updatePeriodValue) {
        return DataSourceCriteria.builder()
                .identifier(IdentifierObjectFactory.dataSourceName(DATA_SOURCE_TYPE_DEFAULT))
                .updatePeriodType(updatePeriodType)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria criteria(DataSourceIdentifier identifier, UpdatePeriodType updatePeriodType) {
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(updatePeriodType)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static DataSourceCriteria criteriaSecond(DataSourceIdentifier identifier, boolean enabled) {
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(UPDATE_PERIOD_TYPE_DEFAULT)
                .xid(Xid.dataSource())
                .enabled(enabled)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static DataSourceCriteria criteriaPeriodTypeAny(DataSourceIdentifier identifier, boolean enabled) {
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(UpdatePeriodType.ANY)
                .xid(Xid.dataSource())
                .enabled(enabled)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static DataSourceCriteria virtualDataSourceSecond(Xid xid) {
        return DataSourceCriteria.builder()
                .identifier(IdentifierObjectFactory.dataSourceName(DATA_SOURCE_TYPE_DEFAULT))
                .updatePeriodType(UPDATE_PERIOD_TYPE_DEFAULT)
                .xid(xid)
                .enabled(true)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
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

    @Override
    public String toString() {
        return format("update period type: {0}, value: {1}", updatePeriodType, updatePeriodValue);
    }
}
