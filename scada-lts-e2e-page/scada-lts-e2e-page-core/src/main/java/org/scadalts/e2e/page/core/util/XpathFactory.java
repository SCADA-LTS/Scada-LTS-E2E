package org.scadalts.e2e.page.core.util;

import org.scadalts.e2e.page.core.criteria.ObjectCriteria;

import java.text.MessageFormat;

public interface XpathFactory {

    static String xpathFindTrByClassCss(ObjectCriteria criteria, String classCss) {
        return MessageFormat.format(".//tr[contains(., ''{0}'') and contains(., ''{1}'') and contains(@class, ''{2}'') ]",
                criteria.getIdentifier(), criteria.getType().getName(), classCss);
    }

    static String xpathFindTr(ObjectCriteria criteria) {
        return MessageFormat.format(".//tr[contains(., ''{0}'') and contains(., ''{1}'') ]",
                criteria.getIdentifier(), criteria.getType().getName());
    }

    static String xpathFind(ObjectCriteria criteria, String tag) {
        return MessageFormat.format(".//{0}[contains(., ''{1}'') and contains(., ''{2}'') ]", tag,
                criteria.getIdentifier(), criteria.getType().getName());
    }

    static String xpathEveryXElementFirst(int every, String tag) {
        return MessageFormat.format(".//{0}[position() mod {1} = 1]", tag, String.valueOf(every));
    }

    static String xpathEveryXElementFirstFromTBodyById(int every, String id) {
        return MessageFormat.format(".//tbody[@id = ''{0}'']/tr/td[position() mod {1} = 1]", id, String.valueOf(every));
    }
}
