package org.scadalts.e2e.page.impl.criterias;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.DataPointType;

@Data
@ToString
public class JmxDataPointCriteria extends DataPointCriteria {

    private final String objectName;
    private final String attributeName;
    private final String compositeItemName;

    @Builder
    public JmxDataPointCriteria(@NonNull Xid xid, @NonNull DataPointIdentifier identifier, boolean settable, boolean enabled, @NonNull DataPointProperties dataPointProperties, String objectName, String attributeName, String compositeItemName) {
        super(xid, identifier, settable, enabled, dataPointProperties);
        this.objectName = objectName;
        this.attributeName = attributeName;
        this.compositeItemName = compositeItemName;
    }

    public static JmxDataPointCriteria empty() {
        return JmxDataPointCriteria.builder()
                .identifier(new DataPointIdentifier("", DataPointType.NONE))
                .dataPointProperties(DataPointProperties.empty())
                .settable(false)
                .enabled(false)
                .xid(new Xid(""))
                .compositeItemName("")
                .attributeName("")
                .objectName("")
                .build();
    }

    public static JmxDataPointCriteria jdbcActive() {
        DataPointType dataPointType = DataPointType.NUMERIC;
        Xid xid = Xid.dataPoint();
        return JmxDataPointCriteria.builder()
                .identifier(new DataPointIdentifier("Active connection count [jdbc pool]", dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .settable(true)
                .enabled(true)
                .xid(xid)
                .objectName("Catalina:class=javax.sql.DataSource,context=/Scada-LTS,host=localhost,name=\"jdbc/scadalts\",type=DataSource")
                .attributeName("active")
                .compositeItemName("")
                .build();
    }

    public static JmxDataPointCriteria jdbcActive(String dataPointXid) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        Xid xid = new Xid(dataPointXid);
        return JmxDataPointCriteria.builder()
                .identifier(new DataPointIdentifier("Active connection count [jdbc pool]", dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .settable(true)
                .enabled(true)
                .xid(xid)
                .objectName("Catalina:class=javax.sql.DataSource,context=/Scada-LTS,host=localhost,name=\"jdbc/scadalts\",type=DataSource")
                .attributeName("active")
                .compositeItemName("")
                .build();
    }
}
