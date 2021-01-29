package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criterias.properties.DataPointTextRendererProperties;
import org.scadalts.e2e.page.impl.dicts.TextRendererType;
import org.scadalts.e2e.service.impl.services.datapoint.TextRenderer;

import static org.scadalts.e2e.test.impl.utils.Converter.convert;

public class DataPointTextRendererPropertiesAdapter extends DataPointTextRendererProperties {
    DataPointTextRendererPropertiesAdapter(TextRenderer textRenderer) {
        super(TextRendererType.getType(textRenderer.getType()),
                textRenderer.getSuffix(),
                textRenderer.getFormat(),
                textRenderer.getConversionExponent(),
                convert(textRenderer.getRangeValues()));
    }
}
