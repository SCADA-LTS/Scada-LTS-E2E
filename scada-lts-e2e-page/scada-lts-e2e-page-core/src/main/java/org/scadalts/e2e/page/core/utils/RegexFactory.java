package org.scadalts.e2e.page.core.utils;

import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

public class RegexFactory {

    public static String between(IdentifierObject source, IdentifierObject target) {
        return source.getValue() + "(.+?)" + target.getValue();
    }

    public static String betweenIdentifierType(CriteriaObject source) {
        return source.getIdentifier().getValue() + ((source.getType() == EmptyType.ANY) ? "" : "(.+?)" + source.getType().getName());
    }

    public static String identifier(IdentifierObject identifier) {
        return identifier.getValue();
    }
}
