package org.scadalts.e2e.page.impl.criteria;

import lombok.Builder;
import lombok.Data;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;
import org.scadalts.e2e.page.core.util.XpathFactory;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;

@Data
@Builder
public class DataPointCriteria implements ObjectCriteria {

    private final String identifier;
    private final DataPointType type;
    private final ChangeType changeType;
    private final String startValue;

    public static DataPointCriteria binaryAlternate() {
        String dataPointName = "dp_test" + System.nanoTime();
        DataPointType dataPointType = DataPointType.BINARY;
        ChangeType changeType = ChangeType.ALTERNATE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(dataPointName)
                .startValue("true")
                .build();
    }

    public static DataPointCriteria noChange(DataPointType dataPointType, String startValue) {
        String dataPointName = "dp_test" + System.nanoTime();
        ChangeType changeType = ChangeType.NO_CHANGE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(dataPointName)
                .startValue(startValue)
                .build();
    }

    public static DataPointCriteria numericNoChange(int startValue) {
        String dataPointName = "dp_test" + System.nanoTime();
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(dataPointName)
                .startValue(String.valueOf(startValue))
                .build();
    }

    public static DataPointCriteria numericNoChange(String identifier) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(identifier)
                .startValue("123")
                .build();
    }

    public static DataPointCriteria criteria(DataPointType dataPointType, String startValue, ChangeType changeType) {
        String dataPointName = "dp_test" + System.nanoTime();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(dataPointName)
                .startValue(startValue)
                .build();
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("tr", identifier, type.getName());
    }
}
