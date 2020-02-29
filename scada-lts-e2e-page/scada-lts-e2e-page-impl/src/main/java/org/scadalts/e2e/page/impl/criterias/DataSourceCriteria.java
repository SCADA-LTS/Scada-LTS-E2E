package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.utils.XpathFactory;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class DataSourceCriteria implements CriteriaObject {

    private final @NonNull Xid xid;
    private final @NonNull DataSourceIdentifier identifier;
    private final @NonNull DataSourceType type;
    private final @NonNull UpdatePeriodType updatePeriodType;
    private final int updatePeriodValue;

    private DataSourceCriteria(Xid xid, DataSourceIdentifier identifier, DataSourceType type, UpdatePeriodType updatePeriodType, int updatePeriodValue) {
        this.xid = xid == null ? Xid.xidDataSource() : xid;
        this.identifier = identifier;
        this.type = type;
        this.updatePeriodType = updatePeriodType;
        this.updatePeriodValue = updatePeriodValue < 1 ? 1 : updatePeriodValue;
    }

    public static DataSourceCriteria virtualDataSourceSecond() {
        String dataSourceName = "ds_test" + System.nanoTime();
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOND;
        Xid xid = Xid.xidDataSource();
        int updatePeriodValue = 2;
        return DataSourceCriteria.builder()
                .identifier(new DataSourceIdentifier(dataSourceName))
                .type(dataSourceType)
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria virtualDataSourceSecond(DataSourceIdentifier identifier) {
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOND;
        Xid xid = Xid.xidDataSource();
        int updatePeriodValue = 2;
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .type(dataSourceType)
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria virtualDataSource(DataSourceIdentifier identifier, UpdatePeriodType updatePeriodType,
                                                       int updatePeriodValue) {
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        Xid xid = Xid.xidDataSource();
        return DataSourceCriteria.builder()
                .identifier(identifier)
                .type(dataSourceType)
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    public static DataSourceCriteria virtualDataSourceSecond(Xid xid) {
        String dataSourceName = "ds_test" + System.nanoTime();
        DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;
        UpdatePeriodType updatePeriodType = UpdatePeriodType.SECOND;
        int updatePeriodValue = 2;
        return DataSourceCriteria.builder()
                .identifier(new DataSourceIdentifier(dataSourceName))
                .type(dataSourceType)
                .updatePeriodType(updatePeriodType)
                .xid(xid)
                .updatePeriodValue(updatePeriodValue)
                .build();
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("tr", "row", identifier.getValue(), type.getName());
    }
}
