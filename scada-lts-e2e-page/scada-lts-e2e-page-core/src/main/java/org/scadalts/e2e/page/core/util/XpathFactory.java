package org.scadalts.e2e.page.core.util;

import org.scadalts.e2e.page.core.criteria.ObjectCriteria;

import java.text.MessageFormat;

abstract class XpathFactory {

    static String xpathFindTrByClassCss(ObjectCriteria criteria, String classCss) {
        return MessageFormat.format(".//tr[contains(., ''{0}'') and contains(., ''{1}'') and contains(@class, ''{2}'') ]",
                criteria.getIdentifier(), criteria.getType().getTypeName(), classCss);
    }

    static String xpathFindTr(ObjectCriteria criteria) {
        return MessageFormat.format(".//tr[contains(., ''{0}'') and contains(., ''{1}'') ]",
                criteria.getIdentifier(), criteria.getType().getTypeName());
    }

    static String xpathFind(ObjectCriteria criteria, String tag) {
        return MessageFormat.format(".//{0}[contains(., ''{1}'') and contains(., ''{2}'') ]", tag,
                criteria.getIdentifier(), criteria.getType().getTypeName());
    }
}
