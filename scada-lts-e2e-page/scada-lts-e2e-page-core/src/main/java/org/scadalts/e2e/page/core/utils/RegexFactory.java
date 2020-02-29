package org.scadalts.e2e.page.core.utils;

import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;

public class RegexFactory {

    public static String between(CriteriaObject source, CriteriaObject target) {
        return source.getIdentifier().getValue() + "(.+?)" + target.getIdentifier().getValue();
    }

    public static String betweenIdentifierType(CriteriaObject source) {
        return source.getIdentifier().getValue() + ((source.getType() == EmptyType.ANY) ? "" : "(.+?)" + source.getType().getName());
    }

    public static String identifier(CriteriaObject source) {
        return source.getIdentifier().getValue();
    }
}
