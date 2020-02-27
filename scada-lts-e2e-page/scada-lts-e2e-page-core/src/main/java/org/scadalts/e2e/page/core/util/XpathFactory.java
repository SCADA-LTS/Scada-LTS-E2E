package org.scadalts.e2e.page.core.util;

import java.text.MessageFormat;

public interface XpathFactory {

    static String xpath(String tag, String arg1) {
        return MessageFormat.format(".//{0}[contains(., ''{1}'')]", tag,
                arg1);
    }

    static String xpath(String tag, String arg1, String arg2) {
        return MessageFormat.format(".//{0}[contains(., ''{1}'') and contains(., ''{2}'') ]", tag,
                arg1, arg2);
    }

    static String xpath(String tag, String cssClass, String arg1, String arg2) {
        return MessageFormat.format(".//{0}[contains(., ''{1}'') and contains(., ''{2}'') and contains(@class, ''{3}'') ]",
                tag, arg1, arg2, cssClass);
    }

    static String xpathEveryXElementFirst(int every, String tag) {
        return MessageFormat.format(".//{0}[position() mod {1} = 1]", tag, String.valueOf(every));
    }
}
