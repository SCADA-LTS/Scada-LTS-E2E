package org.scadalts.e2e.page.core.utils;

import org.scadalts.e2e.page.core.criterias.CssClass;
import org.scadalts.e2e.page.core.criterias.Tag;

import java.text.MessageFormat;

public interface XpathFactory {

    static String xpath(Tag tag, String arg1) {
        return MessageFormat.format(".//{0}[contains(., ''{1}'')]", tag.getValue(),
                arg1);
    }

    static String xpath(Tag tag, String arg1, String arg2) {
        return MessageFormat.format(".//{0}[contains(., ''{1}'') and contains(., ''{2}'') ]", tag.getValue(),
                arg1, arg2);
    }

    static String xpath(Tag tag, CssClass cssClass, String arg1, String arg2) {
        return MessageFormat.format(".//{0}[contains(., ''{1}'') and contains(., ''{2}'') and contains(@class, ''{3}'') ]",
                tag.getValue(), arg1, arg2, cssClass.getValue());
    }

    static String xpath(Tag tag, CssClass cssClass, String arg1) {
        return MessageFormat.format(".//{0}[contains(., ''{1}'') and contains(@class, ''{3}'') ]",
                tag.getValue(), arg1, cssClass.getValue());
    }

    static String xpathEveryXElementFirst(int every, Tag tag) {
        return MessageFormat.format(".//{0}[position() mod {1} = 1]", tag.getValue(), String.valueOf(every));
    }

    static String xpathTagCssClassArg(Tag tag, CssClass cssClass, String arg1) {
        return MessageFormat.format(".//{0}[contains(@class, ''{1}'') and contains(., ''{2}'')]",
                tag.getValue(), cssClass.getValue(), arg1);
    }
}
