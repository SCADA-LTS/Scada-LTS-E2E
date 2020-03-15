package org.scadalts.e2e.page.core.xpaths;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class XpathAttribute {

    private final String Name;
    private final String value;

    private XpathAttribute(String Name, String value) {
        this.Name = Name;
        this.value = value;
    }

    public static XpathAttribute onclick(String value) {
        return new XpathAttribute("@onclick", value);
    }

    public static XpathAttribute id(String value) {
        return new XpathAttribute("@id", value);
    }

    public static XpathAttribute clazz(String value) {
        return new XpathAttribute("@class", value);
    }

    public static XpathAttribute text(String value) {
        return new XpathAttribute("text()", value);
    }

    public static XpathAttribute empty() {
        return new XpathAttribute(".", "");
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(value);
    }
}
