package org.scadalts.e2e.page.impl.groovy;

import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;

public class CriteriaUtil {

    public static DataPointCriteria dataPoint() {
        return DataPointCriteria.binaryNoChange();
    }

    public static DataPointCriteria dataPointBinary() {
        return dataPoint();
    }

    public static DataPointCriteria dataPointNumeric() {
        return DataPointCriteria.numericNoChange();
    }

    public static DataSourceCriteria dataSource() {
        return DataSourceCriteria.virtualDataSourceSecond();
    }

    public static DataSourcePointCriteria dataSourcePoint() {
        return DataSourcePointCriteria.virtualDataSourceNumericNoChange();
    }

    public static DataSourcePointCriteria dataSourcePoint(DataSourceCriteria dataSourceCriteria,
                                                          DataPointCriteria dataPointCriteria) {
        return DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);
    }

    public static DataSourcePointCriteria dataSourcePoint(DataPointCriteria dataPointCriteria,
                                                          DataSourceCriteria dataSourceCriteria) {
        return DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);
    }

    public static EventDetectorCriteria eventDetector(DataSourcePointCriteria dataSourcePointCriteria) {
        return EventDetectorCriteria.changeAlarmLevelNone(dataSourcePointCriteria);
    }

    public static EventHandlerCriteria eventHandlerSms(EventDetectorCriteria eventDetectorCriteria) {
        return EventHandlerCriteria.activeScript(IdentifierObjectFactory.eventHandlerName(EventHandlerType.SMS),
                eventDetectorCriteria, ScriptCriteria.empty());
    }

    public static EventHandlerCriteria eventHandlerEmail(EventDetectorCriteria eventDetectorCriteria) {
        return EventHandlerCriteria.activeScript(IdentifierObjectFactory.eventHandlerName(EventHandlerType.EMAIL),
                eventDetectorCriteria, ScriptCriteria.empty());
    }
}
