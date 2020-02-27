package org.scadalts.e2e.page.core.util;

import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;

public class RegexFactory {

    public static String allBetween(ObjectCriteria source, ObjectCriteria target) {
        return source.getIdentifier() + "(.+?)" + target.getIdentifier();
    }

    public static String all(ObjectCriteria source) {
        return source.getIdentifier() + ((source.getType() == EmptyType.ANY) ? "" : "(.+?)" + source.getType().getName());
    }
}
