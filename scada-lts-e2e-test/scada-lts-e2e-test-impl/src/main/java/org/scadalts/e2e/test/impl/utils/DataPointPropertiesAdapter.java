package org.scadalts.e2e.test.impl.utils;

import lombok.EqualsAndHashCode;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointChartRenderProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.EngineeringUnitsType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.service.impl.services.datapoint.DataPointPropertiesJson;
import org.scadalts.e2e.service.impl.services.datapoint.EventDetectorJson;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
public class DataPointPropertiesAdapter extends DataPointProperties {

    public DataPointPropertiesAdapter(DataPointPropertiesJson dataPointPropertiesJson) {
        super(EngineeringUnitsType.getType("", dataPointPropertiesJson.getEngineeringUnits()), dataPointPropertiesJson.getChartColour(),
                new DataPointLoggingPropertiesAdapter(dataPointPropertiesJson),
                dataPointPropertiesJson.getChartRenderer() == null ? DataPointChartRenderProperties.empty() : new DataPointChartRenderPropertiesAdapter(dataPointPropertiesJson.getChartRenderer()),
                new DataPointTextRendererPropertiesAdapter(dataPointPropertiesJson.getTextRenderer()),
                _eventDetectors(dataPointPropertiesJson.getEventDetectors()));
    }

    private static List<EventDetectorCriteria> _eventDetectors(List<EventDetectorJson> eventDetectors) {
        return eventDetectors.stream().map(a -> EventDetectorCriteria.builder()
                .identifier(new EventDetectorIdentifier(a.getAlias(),
                        EventDetectorType.getType(a.getDetectorType())))
                .alarmLevel(AlarmLevel.getType(a.getAlarmLevel()))
                .xid(new Xid(a.getXid()))
                .dataSourcePointCriteria(VirtualDataSourcePointCriteria.empty())
                .eventHandlerCriterias(Collections.emptyList())
                .build())
                .collect(Collectors.toList());
    }
}
