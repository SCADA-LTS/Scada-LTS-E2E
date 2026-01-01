package org.scadalts.e2e.page.impl.criterias.properties;

import lombok.*;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.dicts.EngineeringUnit;
import org.scadalts.e2e.page.impl.dicts.EngineeringUnitsType;
import org.scadalts.e2e.page.impl.dicts.TextRendererType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class DataPointProperties {

    private final EngineeringUnit engineeringUnits;

    private final String chartColour;

    private final DataPointLoggingProperties loggingProperties;
    private final DataPointChartRenderProperties chartRenderProperties;
    private final DataPointTextRendererProperties textRendererProperties;
    private final @Singular List<EventDetectorCriteria> eventDetectors;

    protected DataPointProperties(EngineeringUnit engineeringUnits, String chartColour, DataPointLoggingProperties loggingProperties, DataPointChartRenderProperties chartRenderProperties, DataPointTextRendererProperties textRendererProperties, List<EventDetectorCriteria> eventDetectors) {
        this.engineeringUnits = engineeringUnits == null ? EngineeringUnit.any() : engineeringUnits;
        this.chartColour = chartColour;
        this.loggingProperties = loggingProperties == null ? DataPointLoggingProperties.empty() : loggingProperties;
        this.chartRenderProperties = chartRenderProperties == null ? DataPointChartRenderProperties.empty() : chartRenderProperties;
        this.textRendererProperties = textRendererProperties == null ? DataPointTextRendererProperties.empty() : textRendererProperties;
        this.eventDetectors = eventDetectors;
    }

    private static final DataPointProperties EMPTY = DataPointProperties.builder()
            .textRendererProperties(DataPointTextRendererProperties.empty().by(EngineeringUnitsType.Other.NO_UNITS))
            .loggingProperties(DataPointLoggingProperties.empty())
            .engineeringUnits(EngineeringUnitsType.Other.NO_UNITS)
            .chartRenderProperties(DataPointChartRenderProperties.empty())
            .chartColour("")
            .eventDetectors(new ArrayList<>())
            .build();

    public static DataPointProperties empty() {
        return EMPTY;
    }

    public static DataPointProperties properties(DataPointChartRenderProperties dataPointChartRenderProperties) {
        EngineeringUnit engineeringUnit = EngineeringUnitsType.VolumetricFlow.LITERS_PER_SECOND;
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(dataPointChartRenderProperties)
                .engineeringUnits(engineeringUnit)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(DataPointTextRendererProperties.plain().by(engineeringUnit))
                .build();
    }


    public static DataPointProperties properties(EngineeringUnit engineeringUnit) {
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(engineeringUnit)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(DataPointTextRendererProperties.plain().by(engineeringUnit))
                .build();
    }

    public static DataPointProperties properties(DataPointLoggingProperties dataPointLoggingProperties) {
        EngineeringUnit engineeringUnit = EngineeringUnitsType.VolumetricFlow.LITERS_PER_SECOND;
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(engineeringUnit)
                .loggingProperties(dataPointLoggingProperties)
                .textRendererProperties(DataPointTextRendererProperties.plain().by(engineeringUnit))
                .build();
    }

    public static DataPointProperties properties(DataPointTextRendererProperties dataPointTextRendererProperties) {
        EngineeringUnit engineeringUnit = EngineeringUnitsType.VolumetricFlow.LITERS_PER_SECOND;
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(engineeringUnit)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(dataPointTextRendererProperties.by(engineeringUnit))
                .build();
    }

    public static DataPointProperties properties(EventDetectorCriteria... eventDetectorCriterias) {
        EngineeringUnit engineeringUnit = EngineeringUnitsType.VolumetricFlow.LITERS_PER_SECOND;
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(engineeringUnit)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(DataPointTextRendererProperties.plain().by(engineeringUnit))
                .eventDetectors(Arrays.asList(eventDetectorCriterias))
                .build();
    }

    public static DataPointProperties properties(List<EventDetectorCriteria> eventDetectorCriterias) {
        EngineeringUnit engineeringUnit = EngineeringUnitsType.VolumetricFlow.LITERS_PER_SECOND;
        return DataPointProperties.builder()
                .chartColour(null)
                .chartRenderProperties(DataPointChartRenderProperties.none())
                .engineeringUnits(engineeringUnit)
                .loggingProperties(DataPointLoggingProperties.noChange())
                .textRendererProperties(DataPointTextRendererProperties.plain().by(engineeringUnit))
                .eventDetectors(eventDetectorCriterias)
                .build();
    }


    public boolean isEmpty() {
        return this == EMPTY;
    }


}
