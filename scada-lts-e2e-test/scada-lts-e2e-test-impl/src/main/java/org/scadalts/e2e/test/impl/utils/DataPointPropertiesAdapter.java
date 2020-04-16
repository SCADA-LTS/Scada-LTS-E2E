package org.scadalts.e2e.test.impl.utils;

import lombok.EqualsAndHashCode;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointChartRenderProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.EngineeringUnit;
import org.scadalts.e2e.service.impl.services.datapoint.DataPointPropertiesJson;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
public class DataPointPropertiesAdapter extends DataPointProperties {

    public DataPointPropertiesAdapter(DataPointPropertiesJson dataPointPropertiesJson) {
        super(EngineeringUnit.getType("", dataPointPropertiesJson.getEngineeringUnits()), dataPointPropertiesJson.getChartColour(),
                new DataPointLoggingPropertiesAdapter(dataPointPropertiesJson),
                dataPointPropertiesJson.getChartRenderer() == null ? DataPointChartRenderProperties.empty() : new DataPointChartRenderPropertiesAdapter(dataPointPropertiesJson.getChartRenderer()),
                new DataPointTextRendererPropertiesAdapter(dataPointPropertiesJson.getTextRenderer()),
                new ArrayList<>());
    }
}
