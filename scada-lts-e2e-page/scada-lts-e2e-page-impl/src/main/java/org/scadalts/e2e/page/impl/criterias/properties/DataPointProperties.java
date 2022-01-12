package org.scadalts.e2e.page.impl.criterias.properties;

import lombok.*;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.dicts.EngineeringUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class DataPointProperties {

    private final DictionaryObject engineeringUnits;

    private final String chartColour;

    private final DataPointLoggingProperties loggingProperties;
    private final DataPointChartRenderProperties chartRenderProperties;
    private final DataPointTextRendererProperties textRendererProperties;
    private final @Singular List<EventDetectorCriteria> eventDetectors;

    protected DataPointProperties(DictionaryObject engineeringUnits, String chartColour, DataPointLoggingProperties loggingProperties, DataPointChartRenderProperties chartRenderProperties, DataPointTextRendererProperties textRendererProperties, List<EventDetectorCriteria> eventDetectors) {
        this.engineeringUnits = engineeringUnits == null ? DictionaryObject.ANY : engineeringUnits;
        this.chartColour = chartColour;
        this.loggingProperties = loggingProperties == null ? DataPointLoggingProperties.empty() : loggingProperties;
        this.chartRenderProperties = chartRenderProperties == null ? DataPointChartRenderProperties.empty() : chartRenderProperties;
        this.textRendererProperties = textRendererProperties == null ? DataPointTextRendererProperties.empty() : textRendererProperties;
        this.eventDetectors = eventDetectors;
    }

    private static final DataPointProperties EMPTY = DataPointProperties.builder()
            .textRendererProperties(DataPointTextRendererProperties.empty())
            .loggingProperties(DataPointLoggingProperties.empty())
            .engineeringUnits(EngineeringUnit.Other.NO_UNITS)
            .chartRenderProperties(DataPointChartRenderProperties.empty())
            .chartColour("")
            .eventDetectors(new ArrayList<>())
            .build();

    public static DataPointProperties empty() {
        return EMPTY;
    }

    public static DataPointProperties properties(DataPointChartRenderProperties dataPointChartRenderProperties) {
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(dataPointChartRenderProperties)
                .engineeringUnits(EngineeringUnit.VolumetricFlow.LITERS_PER_SECOND)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(DataPointTextRendererProperties.plain())
                .build();
    }


    public static DataPointProperties properties(DictionaryObject unit) {
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(unit)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(DataPointTextRendererProperties.plain())
                .build();
    }

    public static DataPointProperties properties(DataPointLoggingProperties dataPointLoggingProperties) {
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(EngineeringUnit.VolumetricFlow.LITERS_PER_SECOND)
                .loggingProperties(dataPointLoggingProperties)
                .textRendererProperties(DataPointTextRendererProperties.plain())
                .build();
    }

    public static DataPointProperties properties(DataPointTextRendererProperties dataPointTextRendererProperties) {
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(EngineeringUnit.VolumetricFlow.LITERS_PER_SECOND)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(dataPointTextRendererProperties)
                .build();
    }

    public static DataPointProperties properties(EventDetectorCriteria... eventDetectorCriterias) {
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(EngineeringUnit.VolumetricFlow.LITERS_PER_SECOND)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(DataPointTextRendererProperties.plain())
                .eventDetectors(Arrays.asList(eventDetectorCriterias))
                .build();
    }

    public static DataPointProperties properties(List<EventDetectorCriteria> eventDetectorCriterias) {
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(EngineeringUnit.VolumetricFlow.LITERS_PER_SECOND)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(DataPointTextRendererProperties.plain())
                .eventDetectors(eventDetectorCriterias)
                .build();
    }


    public boolean isEmpty() {
        return this == EMPTY;
    }
}
