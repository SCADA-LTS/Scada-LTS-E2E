package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.dicts.IntervalLoggingPeriodType;
import org.scadalts.e2e.page.impl.dicts.IntervalLoggingType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;
import org.scadalts.e2e.page.impl.dicts.PurgeType;
import org.scadalts.e2e.service.impl.services.datapoint.DataPointPropertiesJson;

public class DataPointLoggingPropertiesAdapter extends DataPointLoggingProperties {
    DataPointLoggingPropertiesAdapter(DataPointPropertiesJson dataPointPropertiesJson) {
        super(LoggingType.getType(dataPointPropertiesJson.getLoggingType()),
                IntervalLoggingPeriodType.getType(dataPointPropertiesJson.getIntervalLoggingPeriodType()),
                IntervalLoggingType.getType(dataPointPropertiesJson.getIntervalLoggingType()),
                PurgeType.getType(dataPointPropertiesJson.getPurgeType()),
                dataPointPropertiesJson.getDefaultCacheSize(), dataPointPropertiesJson.isDiscardExtremeValues(),
                dataPointPropertiesJson.getDiscardHighLimit(), dataPointPropertiesJson.getDiscardLowLimit(),
                dataPointPropertiesJson.getIntervalLoggingPeriod(), dataPointPropertiesJson.getPurgePeriod(),
                dataPointPropertiesJson.getTolerance());
    }
}
