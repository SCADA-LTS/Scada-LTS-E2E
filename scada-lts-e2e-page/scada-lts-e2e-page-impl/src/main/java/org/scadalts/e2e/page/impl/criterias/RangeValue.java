package org.scadalts.e2e.page.impl.criterias;

import lombok.Builder;
import lombok.Data;
import org.scadalts.e2e.page.impl.dicts.Color;

@Data
@Builder
public class RangeValue {

    private final Color colour;
    private final String from;
    private final String to;
    private final String text;

}
