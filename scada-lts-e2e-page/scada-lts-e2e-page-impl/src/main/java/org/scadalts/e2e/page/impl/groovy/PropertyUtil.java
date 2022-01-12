package org.scadalts.e2e.page.impl.groovy;

import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.GetXid;

public class PropertyUtil {

    public static String name(CriteriaObject criteria) {
        return criteria.getIdentifier().getValue();
    }

    public static <T extends GetXid> String xid(T criteria) {
        return criteria.getXid().getValue();
    }

    public static String type(CriteriaObject criteria) {
        return criteria.getIdentifier().getType().getName();
    }
}
