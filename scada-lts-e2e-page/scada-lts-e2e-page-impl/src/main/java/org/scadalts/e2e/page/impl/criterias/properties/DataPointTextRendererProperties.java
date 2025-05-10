package org.scadalts.e2e.page.impl.criterias.properties;

import lombok.*;
import org.scadalts.e2e.page.impl.criterias.RangeValue;
import org.scadalts.e2e.page.impl.dicts.Color;
import org.scadalts.e2e.page.impl.dicts.EngineeringUnit;
import org.scadalts.e2e.page.impl.dicts.TextRendererType;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class DataPointTextRendererProperties {
    private final TextRendererType textRendererType;
    private final String suffix;
    private final String format;
    private final int conversionExponent;
    private final @Singular List<RangeValue> rangeValues;

    private static final DataPointTextRendererProperties EMPTY = plain();

    public static DataPointTextRendererProperties empty() {
        return EMPTY;
    }

    public DataPointTextRendererProperties by(EngineeringUnit engineeringUnit) {
        return DataPointTextRendererProperties.builder()
                .rangeValues(rangeValues)
                .conversionExponent(conversionExponent)
                .format(format)
                .suffix(textRendererType == TextRendererType.ANALOG || textRendererType == TextRendererType.PLAIN ? " " + engineeringUnit.getUnitSuffix() : "")
                .textRendererType(textRendererType)
                .build();
    }

    public static DataPointTextRendererProperties plain() {
        return DataPointTextRendererProperties.builder()
                .rangeValue(RangeValue.builder()
                        .text("")
                        .to("0.0")
                        .from("0.0")
                        .colour(Color.WHITE)
                        .build())
                .conversionExponent(0)
                .format("")
                .suffix("")
                .textRendererType(TextRendererType.PLAIN)
                .build();
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
