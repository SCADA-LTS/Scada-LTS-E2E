package org.scadalts.e2e.page.impl.criterias;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;

import javax.validation.constraints.Min;
import java.util.Objects;

import static java.text.MessageFormat.format;

@Data
public class UpdateDataSourceCriteria extends DataSourceCriteria {

    private final @NonNull UpdatePeriodType updatePeriodType;
    private final @Min(1) int updatePeriodValue;

    private static final UpdatePeriodType UPDATE_PERIOD_TYPE_DEFAULT = UpdatePeriodType.SECOND;
    private static final int UPDATE_PERIOD_VALUE_DEFAULT = 1;
    private static final DataSourceType DATA_SOURCE_TYPE_DEFAULT = DataSourceType.VIRTUAL_DATA_SOURCE;

    public UpdateDataSourceCriteria() {
        super();
        this.updatePeriodType = UPDATE_PERIOD_TYPE_DEFAULT;
        this.updatePeriodValue = UPDATE_PERIOD_VALUE_DEFAULT;
    }

    public UpdateDataSourceCriteria(@NonNull DataSourceCriteria dataSource) {
        super(dataSource.getXid(), dataSource.getIdentifier(), dataSource.isEnabled());
        this.updatePeriodType = UpdatePeriodType.MINUTE;
        this.updatePeriodValue = 5;
    }

    public UpdateDataSourceCriteria(@NonNull DataSourceCriteria dataSource,
                                    @NonNull UpdatePeriodType updatePeriodType,
                                    int updatePeriodValue) {
        super(dataSource.getXid(), dataSource.getIdentifier(), dataSource.isEnabled());
        this.updatePeriodType = updatePeriodType;
        this.updatePeriodValue = updatePeriodValue;
    }

    @Builder
    public UpdateDataSourceCriteria(@NonNull Xid xid, @NonNull DataSourceIdentifier identifier, boolean enabled,
                                    @NonNull UpdatePeriodType updatePeriodType, int updatePeriodValue) {
        super(xid, identifier, enabled);
        this.updatePeriodType = updatePeriodType;
        this.updatePeriodValue = updatePeriodValue;
    }

    public static UpdateDataSourceCriteria empty() {
        return UpdateDataSourceCriteria.builder()
                .identifier(new DataSourceIdentifier("", DataSourceType.NONE))
                .updatePeriodType(UPDATE_PERIOD_TYPE_DEFAULT)
                .xid(new Xid(""))
                .enabled(true)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static UpdateDataSourceCriteria virtualDataSourceSecond() {
        return UpdateDataSourceCriteria.builder()
                .identifier(IdentifierObjectFactory.dataSourceName(DATA_SOURCE_TYPE_DEFAULT))
                .updatePeriodType(UPDATE_PERIOD_TYPE_DEFAULT)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static UpdateDataSourceCriteria criteriaSecond(DataSourceIdentifier identifier) {
        return UpdateDataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(UPDATE_PERIOD_TYPE_DEFAULT)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static UpdateDataSourceCriteria criteria(DataSourceIdentifier identifier, UpdatePeriodType updatePeriodType,
                                                    int updatePeriodValue) {
        return UpdateDataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(updatePeriodType)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static UpdateDataSourceCriteria criteria(DataSourceIdentifier identifier, UpdatePeriodType updatePeriodType,
                                                    int updatePeriodValue, boolean enabled) {
        return UpdateDataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(updatePeriodType)
                .xid(Xid.dataSource())
                .enabled(enabled)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static UpdateDataSourceCriteria virtualDataSource(UpdatePeriodType updatePeriodType, int updatePeriodValue) {
        return UpdateDataSourceCriteria.builder()
                .identifier(IdentifierObjectFactory.dataSourceName(DATA_SOURCE_TYPE_DEFAULT))
                .updatePeriodType(updatePeriodType)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static UpdateDataSourceCriteria criteria(DataSourceIdentifier identifier, UpdatePeriodType updatePeriodType) {
        return UpdateDataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(updatePeriodType)
                .xid(Xid.dataSource())
                .enabled(true)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static UpdateDataSourceCriteria criteriaSecond(DataSourceIdentifier identifier, boolean enabled) {
        return UpdateDataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(UPDATE_PERIOD_TYPE_DEFAULT)
                .xid(Xid.dataSource())
                .enabled(enabled)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static UpdateDataSourceCriteria criteriaPeriodTypeAny(DataSourceIdentifier identifier, boolean enabled) {
        return UpdateDataSourceCriteria.builder()
                .identifier(identifier)
                .updatePeriodType(UpdatePeriodType.ANY)
                .xid(Xid.dataSource())
                .enabled(enabled)
                .updatePeriodValue(UPDATE_PERIOD_VALUE_DEFAULT)
                .build();
    }

    public static UpdateDataSourceCriteria virtualDataSourceSecond(Xid xid) {
        return UpdateDataSourceCriteria.builder()
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
        if (!(o instanceof UpdateDataSourceCriteria)) return false;
        UpdateDataSourceCriteria that = (UpdateDataSourceCriteria) o;
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
