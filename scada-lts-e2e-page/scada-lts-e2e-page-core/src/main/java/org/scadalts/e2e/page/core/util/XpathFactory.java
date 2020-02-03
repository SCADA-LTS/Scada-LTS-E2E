package org.scadalts.e2e.page.core.util;

import org.scadalts.e2e.page.core.criteria.ObjectCriteria;

import java.text.MessageFormat;

public abstract class XpathFactory {

    public static String xpathFindTrByClassCss(ObjectCriteria criteria, String classCss) {
        return MessageFormat.format(".//tr[contains(., ''{0}'') and contains(., ''{1}'') and contains(@class, ''{2}'') ]",
                criteria.getName(), criteria.getType().getTypeName(), classCss);
    }


    public static String xpathFindTr(ObjectCriteria criteria) {
        return MessageFormat.format(".//tr[contains(., ''{0}'') and contains(., ''{1}'') ]",
                criteria.getName(), criteria.getType().getTypeName());
    }
}
