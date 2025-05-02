package org.scadalts.e2e.page.impl.groovy;

import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;

public class CriteriaUtil {

    public static VirtualDataPointCriteria dataPoint() {
        return VirtualDataPointCriteria.binaryNoChange();
    }

    public static VirtualDataPointCriteria dataPointBinary() {
        return dataPoint();
    }

    public static VirtualDataPointCriteria dataPointNumeric() {
        return VirtualDataPointCriteria.numericNoChange();
    }

    public static UpdateDataSourceCriteria dataSource() {
        return UpdateDataSourceCriteria.virtualDataSourceSecond();
    }

    public static DataSourcePointCriteria<UpdateDataSourceCriteria, VirtualDataPointCriteria> dataSourcePoint() {
        return VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange();
    }

    public static DataSourcePointCriteria<UpdateDataSourceCriteria, VirtualDataPointCriteria> dataSourcePoint(UpdateDataSourceCriteria dataSourceCriteria,
                                                                                                                     VirtualDataPointCriteria dataPointCriteria) {
        return VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointCriteria);
    }

    public static DataSourcePointCriteria<UpdateDataSourceCriteria, VirtualDataPointCriteria> dataSourcePoint(VirtualDataPointCriteria dataPointCriteria,
                                                                                                                     UpdateDataSourceCriteria dataSourceCriteria) {
        return VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointCriteria);
    }

    public static EventDetectorCriteria eventDetector(VirtualDataSourcePointCriteria dataSourcePointCriteria) {
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
