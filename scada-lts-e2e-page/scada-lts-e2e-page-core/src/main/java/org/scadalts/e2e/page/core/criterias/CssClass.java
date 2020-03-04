package org.scadalts.e2e.page.core.criterias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class CssClass {
    private final String value;

    private final static CssClass EMPTY = new CssClass("");

    public static CssClass empty() {
        return EMPTY;
    }
}
